import { Component, OnInit, ViewChild, ElementRef, HostListener } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { trigger, style, transition, animate, keyframes, state, group } from '@angular/animations';

export const EASE = trigger('ease', [
  state('active', style({ borderRadius: '4px', width: 'auto' })),
  state('inactive', style({ borderRadius: '100%', width: '48px' })),
  transition('inactive => active', [
    animate('1s 0s ease', keyframes([
      style({ borderRadius: '100%', width: '48px', offset: 0 }),
      style({ borderRadius: '4px', width: '48px', offset: 0.25 }),
      style({ borderRadius: '4px', width: '48px', offset: 0.5 }),
      style({ borderRadius: '4px', width: '96px', offset: 1.0 })
    ]))
  ]),
  transition('active => inactive', [
    animate('1s 0s ease', keyframes([
      style({ borderRadius: '4px', width: '96px', offset: 0 }),
      style({ borderRadius: '4px', width: '96px', offset: 0.25 }),
      style({ borderRadius: '4px', width: '96px', offset: 0.5 }),
      style({ borderRadius: '100%', width: '48px', offset: 1.0 })
    ]))
  ]),
])

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss'],
  animations: [
    EASE
  ]
})
export class UserEditComponent implements OnInit {

  @HostListener('document:click', ['$event'])
  handleClickEvent(event: MouseEvent) {
    if (this.show) this.show = false;
  }

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  constructor() { }

  ngOnInit() {
  }

  show;
  toggleOperationMenu(event) {
    event.preventDefault();
    event.stopPropagation();
    this.show = !this.show;
  }
}
