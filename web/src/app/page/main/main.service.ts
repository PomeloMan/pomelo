import { Injectable } from '@angular/core';
import { Subject, of, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApiService } from 'src/app/config/api.service';

import { useMockData } from 'src/app/config/app.constant.js';
import { API } from 'src/app/config/api.js';

import languages from '../../../assets/mock/main/languages.json'

@Injectable()
export class MainService {

    // Observable string sources
    private subject = new Subject<Object>();
    // Observable string streams
    change$ = this.subject.asObservable();
    // Service message commands
    change(data: Object) {
        this.subject.next(data);
    }

    constructor(
        private service: ApiService
    ) { }

    // Common Data
    // Languages
    _languages: Observable<Language[]>;
    listLanguages(keyword?): Observable<Language[]> {
        if (this._languages) {
            if (keyword) {
                return this._languages.pipe(map(ls => {
                    let results = ls.filter(l => l.code.includes(keyword) || l.name.includes(keyword))
                    return results;
                }));
            } else {
                return this._languages;
            }
        } else {
            if (useMockData) {
                this._languages = of(languages);
            } else {
                this.service.get(API.LANGUAGE_LIST_URL)
            }
            return this.listLanguages(keyword);
        }
    }

    listRoles() {

    }
}

export interface PageData {
    hasChildToolbar?: boolean;
}

export interface Language {
    code?: string;
    name?: string;
}

export interface Role {
    id?: string;
    name?: string;
}

export interface SettingMenu {
    id?: string;
    name?: string;
    type?: string; // 'button' | 'link'
    link?: string;
    icon?: string;
}