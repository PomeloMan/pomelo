import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import { StorageService } from '../service/storage.service';
// import 'rxjs/add/operator/toPromise';

export class User {
    username: string;
    password: string;
    displayName: string;
    constructor() { }
}

@Injectable()
export class UserService {

    private headers = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http, private storageService: StorageService) { }

    login(credentials): Promise<User> {
        return this.http.post("http://localhost:8008/login", credentials)
            .toPromise().then((resp) => {
                // console.log(resp);
                // console.log(resp.headers);
                var auth = resp.headers.get('Authorization');
                if (auth) {
                    this.storageService.setData('Authorization', new Headers({ 'Authorization': auth }));
                    return true;
                }
                return false;
            }).catch(this.handleError);
    }

    me(): Promise<User> {
        var auth = this.storageService.getData('Authorization');
        return this.http.get("http://localhost:8008/user/me", { "headers": auth }).toPromise().then(resp => {
            console.log(resp);
            return null;
        }).catch(error => {
            console.error('An error occurred', error);
            return Promise.reject(error.message || error);
        })
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }

}