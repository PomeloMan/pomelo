import { Injectable } from '@angular/core';
import { StorageService } from '../../common/service/storage.service';
import { User } from 'src/app/page/main/system/user/user.service';

export const TOKEN: string = 'token';
export const USER: string = 'user';

@Injectable({
	providedIn: 'root'
})
export class AuthService {

	user: User;
	redirectUrl: string

	constructor(
		private storageService: StorageService
	) { }

	getAuthorizationToken(): string {
		return this.storageService.getItem(TOKEN);
	}

	setAuthorizationToken(token): void {
		this.storageService.setItem(TOKEN, token);
	}

	getCurrentUser(): User {
		if (!this.user) {
			this.user = this.storageService.getItem(USER);
		}
		return this.user;
	}

	setCurrentUser(user: User): void {
		this.user = user;
		this.storageService.setItem(USER, this.user);
	}

	clear(): void {
		this.storageService.removeItem(TOKEN);
		this.storageService.removeItem(USER);
	}
}
