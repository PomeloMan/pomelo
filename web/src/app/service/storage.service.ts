import { Injectable } from '@angular/core';

@Injectable()
export class StorageService {

    constructor() {
        //
    }

    public setData(key: string, value: any) {
        localStorage.setItem(key, JSON.stringify(value));
    }

    public getData(key: string) {
        return JSON.parse(localStorage.getItem(key));
    }

    public removeData(key: string) {
        localStorage.removeItem(key);
    }
}