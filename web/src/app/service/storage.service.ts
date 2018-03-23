import { Injectable } from '@angular/core';

import { Observable } from 'rxjs/Observable';

@Injectable()
export class StorageService {

    storage: Storage;

    constructor() {
    }

    public setDefault() {
        this.setStorage(localStorage);
        this.setStorage(sessionStorage);
    }

    public setStorage(storage: Storage) {
        this.storage = storage;
    }

    public setData(key: string, value: any) {
        this.storage.setItem(key, JSON.stringify(value));
    }

    public getData(key: string) {
        return JSON.parse(this.storage.getItem(key));
    }

    public removeData(key: string) {
        this.storage.removeItem(key);
    }
}