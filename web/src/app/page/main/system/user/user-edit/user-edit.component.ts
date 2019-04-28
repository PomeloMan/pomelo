import { Component, OnInit, HostListener, ViewChild, ElementRef, AfterViewInit, NgZone } from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { trigger, style, transition, animate, keyframes, state, animateChild, query, group } from '@angular/animations';
import { MatAutocompleteSelectedEvent, MatToolbar } from '@angular/material';
import { Observable, forkJoin } from 'rxjs';
import { startWith, map, debounceTime } from 'rxjs/operators';
import { ScrollDispatcher, CdkScrollable } from '@angular/cdk/overlay';
import { User, TodoEvent, UserService } from '../user.service';
import { isNullOrUndefined } from 'util';
import { Router, ActivatedRoute } from '@angular/router';
import { MainService, Language } from '../../../main.service';

import * as _ from 'lodash';

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

  id: any;
  user: User = {};
  languages: Language[];

  @HostListener('document:click', ['$event'])
  handleClickEvent(event: MouseEvent) {
    // if (this.showOperation) this.showOperation = false;
  }

  basicForm = new FormGroup({
    usernameFormCtrl: new FormControl(null, [Validators.required]),
    userPositionFormCtrl: new FormControl(null, [Validators.required]),
    emailFormCtrl: new FormControl(null, [Validators.required, Validators.email]),
    secondaryEmailFormCtrl: new FormControl(null, [Validators.email]),
    phoneNumberFormCtrl: new FormControl(null, [Validators.required, Validators.pattern('[0-9]*')]),
    emergencyContactFormCtrl: new FormControl(null, [Validators.required, Validators.pattern('[0-9]*')])
  });

  userSubscribe: Observable<User>;
  languageSubscribe: Observable<Language[]>;

  constructor(
    private scrollDispatcher: ScrollDispatcher,
    protected router: Router,
    protected route: ActivatedRoute,
    private zone: NgZone,
    private service: UserService,
    private mainService: MainService
  ) { }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');

    this.userSubscribe = this.service.info(this.id);
    this.languageSubscribe = this.mainService.listLanguages();

    this.initData();
  }

  initData() {
    let subs = forkJoin(this.userSubscribe, this.languageSubscribe);
    subs.subscribe(([user, languages]: [User, Language[]]) => {
      // user response
      this.user = user || {};

      // language response
      this.languages = languages;
      this.filteredLanguages = this.languageCtrl.valueChanges.pipe(
        startWith(null),
        map((value: any | null) => typeof value == 'string' ? this._filter(value) : this.languages.slice())
      );
    })

    // this.initUser();
    // this.initLanguages();
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
    _.debounce(() => {
      this.service.save().subscribe(res => { })
    }, 1000, { 'leading': true, 'trailing': false })()

    // this.service.save().pipe(
    //   debounceTime(1000)
    // ).subscribe(res=>{
    // })
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





  private initUser() {
    this.userSubscribe.subscribe((user: User) => {
      this.user = user || {};
    })
  }
  private initLanguages() {
    this.languageSubscribe.subscribe((languages: Language[]) => {
      this.languages = languages;
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
