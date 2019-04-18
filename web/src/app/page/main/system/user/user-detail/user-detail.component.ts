import { Component, OnInit, OnDestroy } from '@angular/core';
import { BaseComponent } from '../../../../../common/component/base.component';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormControl, Validators } from '@angular/forms';
import { User, UserService } from '../user.service';
import { Observable } from 'rxjs';

@Component({
	selector: 'app-user-detail',
	templateUrl: './user-detail.component.html',
	styleUrls: ['./user-detail.component.scss']
})
export class UserDetailComponent extends BaseComponent implements OnInit, OnDestroy {

	id: any;
	user: User = {};

	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public location: Location,
		private service: UserService
	) {
		super(router, route, location);
	}

	emailFormControl = new FormControl('', [
		Validators.required,
		Validators.email,
	]);

	toppingList: string[] = ['Extra cheese', 'Mushroom', 'Onion', 'Pepperoni', 'Sausage', 'Tomato'];

	userSubscribe: Observable<User>;
	ngOnInit() {
		this.id = this.route.snapshot.paramMap.get('id');
		this.userSubscribe = this.service.info(this.id);

		this.initData();
	}

	ngOnDestroy(): void {
	}

	initData() {
		this.userSubscribe.subscribe((user: User) => {
			this.user = user || {};
		})
	}

	tabLoadTimes: Date[] = [];
	getTimeLoaded(index: number) {
		if (!this.tabLoadTimes[index]) {
			this.tabLoadTimes[index] = new Date();
		}
		return this.tabLoadTimes[index];
	}

}