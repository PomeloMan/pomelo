import { Component, OnInit, HostListener, ViewChild, ElementRef, AfterViewInit, NgZone } from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { trigger, style, transition, animate, keyframes, state, animateChild, query, group } from '@angular/animations';
import { MatAutocompleteSelectedEvent, MatChipInputEvent, MatAutocomplete, MatToolbar } from '@angular/material';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { ScrollDispatcher, CdkScrollable } from '@angular/cdk/overlay';
import { User } from '../user.service';
import { isNullOrUndefined } from 'util';

export const EASE = trigger('easeInOut', [
  state('active', style({ borderRadius: '4px', width: 'auto' })),
  state('inactive', style({ borderRadius: '100%', width: '48px' })),
  transition('inactive => active', [
    animate('0.5s 0s ease', keyframes([
      style({ borderRadius: '100%', width: '48px', offset: 0 }),
      style({ borderRadius: '4px', width: '48px', offset: 0.5 }),
      style({ borderRadius: '4px', width: '96px', offset: 1.0 })
    ]))
  ]),
  transition('active => inactive', [
    animate('0.5s 0s ease', keyframes([
      style({ borderRadius: '4px', width: '96px', offset: 0 }),
      style({ borderRadius: '4px', width: '48px', offset: 0.5 }),
      style({ borderRadius: '100%', width: '48px', offset: 1.0 })
    ]))
  ]),
])

// UserToolbar Animation @line 5
export const TRANS_PARENT_ANIMATION = trigger('toolbarAni', [
  state('active', style({ height: '96px' })),
  state('inactive', style({ height: '120px' })),
  transition('inactive <=> active', [
    group([
      query('@toolbarAvatarAni', [
        animateChild()
      ]),
      animate('0.5s 0s ease'),
    ])
  ])
])

// UserToolbar Child Animation @line 7
export const TRANS_CHILD_ANIMATION = trigger('toolbarAvatarAni', [
  state('active', style({ width: '64px', height: '64px', fontSize: '64px' })),
  state('inactive', style({ width: '80px', height: '80px', fontSize: '80px' })),
  transition('inactive <=> active', [
    animate('0.5s 0s ease')
  ])
])

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss'],
  animations: [
    EASE, TRANS_PARENT_ANIMATION, TRANS_CHILD_ANIMATION
  ]
})
export class UserEditComponent implements OnInit, AfterViewInit {

  user: User = {};

  @HostListener('document:click', ['$event'])
  handleClickEvent(event: MouseEvent) {
    if (this.showOperation) this.showOperation = false;
  }

  contactForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    secondaryEmail: new FormControl('', [Validators.email]),
    phoneNumber: new FormControl('', [Validators.required, Validators.pattern('[1-9]*')]),
    emergencyContact: new FormControl('', [Validators.required, Validators.pattern('[1-9]*')])
  });

  constructor(
    private scrollDispatcher: ScrollDispatcher,
    private zone: NgZone
  ) {
    this.filteredFruits = this.fruitCtrl.valueChanges.pipe(
      startWith(null),
      map((fruit: string | null) => fruit ? this._filter(fruit) : this.allFruits.slice()));
  }

  ngOnInit() {
    this.user = {
      username: 'admin',
      displayName: 'administrator',
      position: 'Software engineer',
      email: 'fengchao.z@outlook.com',
      phoneNumber: 13861800672,
      address: '江苏省无锡市梁溪区清一村东塘89号',
      selfIntroduction: 'Germanium (Ge) is a chemical element with atomic number 32. It is a lustrous, hard, greyish-white metalloid in the carbon group, chemically similar to silicon (Si) and tin (Sn). In 1869, Dmitri Mendeleev predicted the existence of germanium (and later some of its properties) based on its position in his periodic table (extract pictured). In 1886, Clemens Winkler discovered the element in a rare mineral called argyrodite.',
      gender: 0,
      avatar: '',
      role: '',
    }
  }

  // material cdkScrollable cdk
  cdkScrollable: CdkScrollable;
  @ViewChild('UserToolbar') userToolbar: MatToolbar;
  scrolled: boolean = false;
  ngAfterViewInit(): void {
    let _this = this;
    let userToolbarElRef = this.userToolbar._elementRef.nativeElement;
    this.cdkScrollable = this.scrollDispatcher.scrollContainers.keys().next().value;
    this.scrollDispatcher.scrolled().subscribe((cdkScrollable: CdkScrollable) => {
      let fromTop = cdkScrollable.measureScrollOffset('top');
      if (fromTop >= 24) {
        if (!userToolbarElRef.classList.contains('mat-elevation-z6')) {
          userToolbarElRef.classList.add('mat-elevation-z6');
          // userToolbarElRef.classList.add('sticky');
          _this.zone.run(() => {
            _this.scrolled = true;
          })
        }
      } else {
        if (userToolbarElRef.classList.contains('mat-elevation-z6')) {
          userToolbarElRef.classList.remove('mat-elevation-z6');
          // userToolbarElRef.classList.remove('sticky');
          _this.zone.run(() => {
            _this.scrolled = false;
          })
        }
      }
    })
  }

  showOperation: boolean = false;// 是否显示操作菜单(@line 16,17,22)
  /**
   * 展开/收起 操作菜单(@line 18)
   * @param event 
   */
  toggleOperationMenu(event) {
    event.preventDefault();
    event.stopPropagation();
    this.showOperation = !this.showOperation;
  }

  save() {
    debugger
  }



  uploadAvatar(event) {
    let _this = this;
    let file = event.target.files[0];

    let reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function (e) {
      _this.user.avatar = this.result;
    }
  }




  allFruits: string[] = ['Apple', 'Lemon', 'Lime', 'Orange', 'Strawberry'];
  fruits: string[] = ['Lemon'];
  fruitCtrl = new FormControl();
  filteredFruits: Observable<string[]>;
  @ViewChild('fruitInput') fruitInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  add(event: MatChipInputEvent): void {
    // Add fruit only when MatAutocomplete is not open
    // To make sure this does not conflict with OptionSelected Event
    if (!this.matAutocomplete.isOpen) {
      const input = event.input;
      const value = event.value;

      // Add our fruit
      if ((value || '').trim()) {
        this.fruits.push(value.trim());
      }

      // Reset the input value
      if (input) {
        input.value = '';
      }

      this.fruitCtrl.setValue(null);
    }
  }

  remove(fruit: string): void {
    const index = this.fruits.indexOf(fruit);

    if (index >= 0) {
      this.fruits.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.fruits.push(event.option.viewValue);
    this.fruitInput.nativeElement.value = '';
    this.fruitCtrl.setValue(null);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.allFruits.filter(fruit => fruit.toLowerCase().indexOf(filterValue) === 0);
  }





  tabLoadTimes: Date[] = [];
  getTimeLoaded(index: number) {
    if (!this.tabLoadTimes[index]) {
      this.tabLoadTimes[index] = new Date();
    }

    return this.tabLoadTimes[index];
  }
}
