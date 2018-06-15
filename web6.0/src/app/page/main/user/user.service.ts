import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { StorageService } from '../../../config/storage.service';
import { ApiService } from '../../../config/api.service';
import { API } from '../../../config/api';

export class User {
    username: string;
    password: string;
    displayName: string;
    constructor() { }
}

@Injectable()
export class UserService {

    private user;

    constructor(
        private service: ApiService,
        private storageService: StorageService
    ) { }

}