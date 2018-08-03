import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { API } from '../../config/api';
import { TOKEN } from '../../auth/auth.service';
import { ApiService } from '../../config/api.service';
import { StorageService } from '../../config/storage.service';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css'],
})

export class LoginComponent implements OnInit {

	constructor(
		private router: Router,
		private api: ApiService,
		private storage: StorageService
	) { }

	ngOnInit(): void {
	}

	login(username: any, password: any) {
		this.api.post(API.AUTH_URL, { username: username, password: password }, { observe: 'response' }).subscribe(
			resp => {
				this.storage.setItem(TOKEN, resp.headers.get('Authorization'));
				this.router.navigate(['/main']);
			},
			error => {
				console.log(error);
			}
		);
	}
}