import { Injectable } from '@angular/core';
import { StorageService } from '../config/storage.service';

export const TOKEN: string = "token";

@Injectable({
  providedIn: 'root'
})
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