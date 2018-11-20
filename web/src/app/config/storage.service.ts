import { Injectable } from '@angular/core';

@Injectable()
export class StorageService {

	storage: Storage;

	constructor() {
		this.setDefault();
	}

	public setDefault() {
		this.setStorage(sessionStorage);
	}

	public setStorage(storage: Storage) {
		this.storage = storage;
	}

	public setItem(key: string, value: any) {
		this.storage.setItem(key, JSON.stringify(value));
	}

	public getItem(key: string) {
		return JSON.parse(this.storage.getItem(key));
	}

	public removeItem(key: string) {
		this.storage.removeItem(key);
	}
}