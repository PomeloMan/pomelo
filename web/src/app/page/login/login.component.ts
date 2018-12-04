import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { API } from '../../config/api';
import { TOKEN } from '../../config/auth.service';
import { ApiService } from '../../config/api.service';
import { StorageService } from '../../config/storage.service';

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
	// form = this.formBuilder.group({username: [''], password: ['']})
	form = new FormGroup({
		username: new FormControl('', [Validators.required, Validators.email]),
		password: new FormControl(''),
	});

	/**
	 * 获取表单验证错误信息
	 * @param control 
	 */
	getErrorMessage(control: FormControl) {
		return control.hasError('required') ? 'You must enter a value' : control.hasError('email') ? 'Not a valid email' : '';
	}

	login() {
		this.api.post(
			API.AUTH_URL,
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