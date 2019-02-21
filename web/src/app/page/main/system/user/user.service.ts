import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/config/api.service';
import { of } from 'rxjs';
import { API } from 'src/app/config/api';

export interface User {
    username: string;
    displayName: string;
    avatar: string;
    role: string;
    createDate: number;
}

@Injectable()
export class UserService {

    constructor(private service: ApiService) { }

    list() {
        return of({ success: 'true', data: USER_LIST })
    }

    page(params) {
        return this.service.post(API.USER_PAGE_URL, {});
    }
}

const USER_LIST: User[] = [
    { username: '1', displayName: '1', avatar: '1', role: '1', createDate: 1 },
    { username: '2', displayName: '2', avatar: '2', role: '2', createDate: 2 },
    { username: '3', displayName: '3', avatar: '3', role: '3', createDate: 3 }
]