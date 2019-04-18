import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { API } from '../../config/api';
import { AuthService } from '../../config/security/auth.service';
import { ApiService } from '../../config/api.service';
import { useMockData } from 'src/app/config/app.constant';
import { User } from '../main/system/user/user.service';

import user from 'src/assets/mock/user/info.json';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.scss'],
})

export class LoginComponent {

	constructor(
		private router: Router,
		private api: ApiService,
		private authService: AuthService
	) {
	}

	// import { FormBuilder } from '@angular/forms';
	// form = this.formBuilder.group({username: [null], password: [null]})
	form = new FormGroup({
		username: new FormControl(null, [Validators.required/**, Validators.email*/]),
		password: new FormControl(null, [Validators.required]),
	});

	/**
	 * 获取表单验证错误信息
	 * @param control 
	 */
	getErrorMessage(control: FormControl) {
		return control.hasError('required') ? 'You must enter a value' : control.hasError('email') ? 'Not a valid email' : '';
	}

	login() {
		if (useMockData) {
			this.authService.setAuthorizationToken('guest');
			this.authService.setCurrentUser(user);
			this.router.navigate(['/main']);
			return;
		}
		this.api.post(
			API.LOGIN_URL,
			{ username: this.form.value.username, password: this.form.value.password },
			{ observe: 'response' })// show full response e.g. '{ body: {code: 10007, msg: "验证码不正确", data: null}, headers: HttpHeaders { normalizedNames: Map(0), lazyUpdate: null, lazyInit: ƒ }, ok: true, status: 200, statusText: "OK", type: 4, url: "http://localhost:8000/login"}'
			.subscribe(
				res => {
					this.authService.setAuthorizationToken(res.headers.get('Authorization'));
					this.api.get(API.USER_INFO_URL).subscribe((user: User) => {
						this.authService.setCurrentUser(user);
						this.router.navigate(['/main']);
					})
				},
				error => {
					console.log(error);
				}
			);
	}
}