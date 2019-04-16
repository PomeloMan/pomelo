import { Component, OnInit, OnDestroy } from '@angular/core';
import { BaseComponent } from '../../../../../common/component/base.component';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormControl, Validators } from '@angular/forms';
import { User } from '../user.service';

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

}