import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/config/api.service';
import { of, Observable } from 'rxjs';
import { API, Page } from 'src/app/config/api';
import { Language } from '../../main.service';

import { useMockData } from 'src/app/config/app.constant';
import info from '../../../../../assets/mock/user/info.json';
import page from '../../../../../assets/mock/user/page.json';

export interface User {
    username?: string;
    displayName?: string;
    position?: string;
    email?: string;
    address?: string;
    selfIntroduction?: string;
    gender?: number;// 0:male / 1:female
    avatar?: any;
    role?: string;
    createdDate?: number;
    languages?: Language[];
    contact?: UserContact
}

export interface UserContact {
    email?: string;
    secondaryEmail?: string;
    phoneNumber?: number;
    emergencyContact?: number;
    facebook?: string;
    twitter?: string;
    wechat?: string;
    weibo?: string;
}

export interface TodoEvent {
    name?: string;
    complete?: boolean;
    createdDate?: number;
}

@Injectable()
export class UserService {

    constructor(
        private service: ApiService
    ) { }

    list(): Observable<User[]> {
        return of(USER_LIST)
        // return this.service.post(API.USER_LIST_URL, {});
    }

    page(params) {
        if (useMockData) {
            return of<Page<User>>(page);
        } else {
            return this.service.post(API.USER_PAGE_URL, {});
        }
    }

    info(id): Observable<User> {
        if (useMockData) {
            return of<User>(info);
        } else {
            return this.service.get(API.USER_INFO_URL);
        }
    }

    save(): Observable<User> {
        return of<User>(info);
    }
}

const USER_LIST: User[] = [
    { username: '1', displayName: '1', avatar: '1', role: '1', createdDate: 1 },
    { username: '2', displayName: '2', avatar: '2', role: '2', createdDate: 2 },
    { username: '3', displayName: '3', avatar: '3', role: '3', createdDate: 3 }
]