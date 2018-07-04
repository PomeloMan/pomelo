import { Component, OnInit } from '@angular/core';
import { NavigationComponent } from '../../../../../common/component/navigation.component';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss']
})
export class UserDetailComponent extends NavigationComponent implements OnInit {

  id: any;
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public location: Location
  ) {
    super(router, route, location);
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    console.log(this.id);
  }

  tabLoadTimes: Date[] = [];

  getTimeLoaded(index: number) {
    if (!this.tabLoadTimes[index]) {
      this.tabLoadTimes[index] = new Date();
    }

    return this.tabLoadTimes[index];
  }
}
