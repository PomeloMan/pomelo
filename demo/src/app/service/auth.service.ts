import { Injectable } from '@angular/core';

import { StorageService } from '../service/storage.service';

export const TOKEN: string = "token";

@Injectable()
export class AuthService {

    redirectUrl: string

    constructor(
        private storageService: StorageService
    ) { }

    getAuthorizationToken(): string {
        return this.storageService.getData(TOKEN);
    }

    setAuthorizationToken(): void {
    }

    clear(): void {
        this.storageService.removeData(TOKEN);
    }
}