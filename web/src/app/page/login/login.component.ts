import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { API } from '../../config/api';
import { TOKEN } from '../../config/security/auth.service';
import { ApiService } from '../../config/api.service';
import { StorageService } from '../../common/service/storage.service';
import { useMockData } from 'src/app/config/app.constant';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.scss'],
})

export class LoginComponent {

	constructor(
		private router: Router,
		private api: ApiService,
		private storage: StorageService
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
			this.storage.setItem(TOKEN, 'guest');
			this.router.navigate(['/main']);
			return;
		}
		this.api.post(
			API.LOGIN_URL,
			{ username: this.form.value.username, password: this.form.value.password },
			{ observe: 'response' })// show full response e.g. '{ body: {code: 10007, msg: "验证码不正确", data: null}, headers: HttpHeaders { normalizedNames: Map(0), lazyUpdate: null, lazyInit: ƒ }, ok: true, status: 200, statusText: "OK", type: 4, url: "http://localhost:8000/login"}'
			.subscribe(
				res => {
					this.storage.setItem(TOKEN, res.headers.get('Authorization'));
					this.router.navigate(['/main']);
				},
				error => {
					console.log(error);
				}
			);
	}
}