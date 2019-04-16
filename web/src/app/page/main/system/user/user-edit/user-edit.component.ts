import { Component, OnInit, HostListener, ViewChild, ElementRef, AfterViewInit, NgZone } from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { trigger, style, transition, animate, keyframes, state, animateChild, query, group } from '@angular/animations';
import { MatAutocompleteSelectedEvent, MatToolbar } from '@angular/material';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { ScrollDispatcher, CdkScrollable } from '@angular/cdk/overlay';
import { User, TodoEvent } from '../user.service';
import { isNullOrUndefined } from 'util';
import { Router } from '@angular/router';
import { MainService, Language } from '../../../main.service';

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

  languages: Language[];
  user: User = {};

  @HostListener('document:click', ['$event'])
  handleClickEvent(event: MouseEvent) {
    if (this.showOperation) this.showOperation = false;
  }

  basicForm = new FormGroup({
    usernameFormCtrl: new FormControl(null, [Validators.required]),
    userPositionFormCtrl: new FormControl(null, [Validators.required]),
    emailFormCtrl: new FormControl(null, [Validators.required, Validators.email]),
    secondaryEmailFormCtrl: new FormControl(null, [Validators.email]),
    phoneNumberFormCtrl: new FormControl(null, [Validators.required, Validators.pattern('[0-9]*')]),
    emergencyContactFormCtrl: new FormControl(null, [Validators.required, Validators.pattern('[0-9]*')])
  });

  constructor(
    private scrollDispatcher: ScrollDispatcher,
    private router: Router,
    private zone: NgZone,
    private mainService: MainService
  ) { }

  ngOnInit() {
    this.user = {
      username: 'admin',
      displayName: 'administrator',
      position: 'Software engineer',
      email: 'fengchao.z@outlook.com',
      address: '江苏省无锡市梁溪区清一村东塘89号',
      selfIntroduction: 'Germanium (Ge) is a chemical element with atomic number 32. It is a lustrous, hard, greyish-white metalloid in the carbon group, chemically similar to silicon (Si) and tin (Sn). In 1869, Dmitri Mendeleev predicted the existence of germanium (and later some of its properties) based on its position in his periodic table (extract pictured). In 1886, Clemens Winkler discovered the element in a rare mineral called argyrodite.',
      gender: 0,
      avatar: '',
      role: '',
      languages: [{
        code: 'zh_CN',
        name: 'Chinese'
      }],
      contact: {
        email: 'fengchao.z@outlook.com',
        secondaryEmail: '13861800672@163.com',
        phoneNumber: 13861800672,
        emergencyContact: 13861800672,
        facebook: '--',
        twitter: '',
        wechat: '13861800672',
        weibo: ''
      }
    }
    this.initLanguages()
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

  delete() {
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


  /** Language Chips */
  languageCtrl = new FormControl();
  filteredLanguages: Observable<Language[]>;
  @ViewChild('languageInput') languageInput: ElementRef<HTMLInputElement>;

  removeChip(language: Language): void {
    const index = this.user.languages.findIndex(l => l.code == language.code);

    if (index >= 0) {
      this.user.languages.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.user.languages.push(event.option.value);
    this.languageInput.nativeElement.value = '';
    this.languageCtrl.setValue(null);
  }

  private _filter(value: string): Language[] {
    return this.languages.filter(language => language.code.toLocaleLowerCase().indexOf(value.toLocaleLowerCase()) > -1 || language.name.toLocaleLowerCase().indexOf(value.toLocaleLowerCase()) > -1);
  }





  tabLoadTimes: Date[] = [];
  getTimeLoaded(index: number) {
    if (!this.tabLoadTimes[index]) {
      this.tabLoadTimes[index] = new Date();
    }

    return this.tabLoadTimes[index];
  }





  /**
   * init languages
   */
  private initLanguages() {
    this.mainService.listLanguages().subscribe(res => {
      this.languages = res;
      this.filteredLanguages = this.languageCtrl.valueChanges.pipe(
        startWith(null),
        map((value: any | null) => typeof value == 'string' ? this._filter(value) : this.languages.slice())
      );
    })
  }




  todos: TodoEvent[] = [{
    name: 'hello world',
    complete: true
  }, {
    name: 'to be continued',
    complete: false
  }];
  /** TODO */
  saveTodo(event) {
    this.todos.unshift({ name: event.target.value, complete: false });
    event.target.value = '';
  }
  clearTodos() {
    this.todos = this.todos.filter(t => !t.complete);
  }
}
