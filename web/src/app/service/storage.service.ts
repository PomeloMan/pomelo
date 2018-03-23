import { Injectable } from '@angular/core';

@Injectable()
export class StorageService {

    storage: Storage

    constructor() {
        this.storage = localStorage;
        // this.storage = sessionStorage;
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