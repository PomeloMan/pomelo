import { Component, OnInit, OnDestroy } from '@angular/core';
import { NavigationComponent } from '../../../../../common/component/navigation.component';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormControl, Validators } from '@angular/forms';
import { trigger, state, transition, animate, style } from '@angular/animations';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss'],
  animations: [
    trigger('expand', [
      state('inactive', style({
        width: '48px'
      })),
      state('active', style({
        width: '96px'
      })),
      transition('inactive <=> active', [
        animate('1000ms ease')
      ])
    ])
  ]
})
export class UserDetailComponent extends NavigationComponent implements OnInit, OnDestroy {

  id: any;
  expanded: boolean = false;

  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public location: Location
  ) {
    super(router, route, location);
  }

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);
  toppingList: string[] = ['Extra cheese', 'Mushroom', 'Onion', 'Pepperoni', 'Sausage', 'Tomato'];

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
  }

  ngOnDestroy(): void {
  }

  tabLoadTimes: Date[] = [];

  getTimeLoaded(index: number) {
    if (!this.tabLoadTimes[index]) {
      this.tabLoadTimes[index] = new Date();
    }

    return this.tabLoadTimes[index];
  }

  expand($event) {
    $event.preventDefault();
    $event.stopPropagation();
    this.expanded = true;
  }

  onClick($event) {
    console.log($event.target)
    this.expanded = false;
  }
}
