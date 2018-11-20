import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { tap } from 'rxjs/operators/tap';
import { catchError } from 'rxjs/operators/catchError';

import { StorageService } from '../../service/storage.service';
import { api } from '../../api';

export class User {
    username: string;
    password: string;
    displayName: string;
    constructor() { }
}

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
};

@Injectable()
export class UserService {

    private user;

    constructor(
        private http: HttpClient,
        private storageService: StorageService
    ) { }

    login(user: User): Observable<HttpResponse<any>> {
        return this.http.post(api.loginUrl, user, { observe: 'response' });
    }

    me(): Promise<User> {
        var auth = this.storageService.getData('Authorization');
        return this.http.post(api.loginUrl, { "headers": auth })
            .toPromise().then(resp => {
                console.log(resp);
                return null;
            }).catch(error => {
                console.error('An error occurred', error);
                return Promise.reject(error.message || error);
            }).catch(this.handleError);
    }

    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {
            // TODO: send the error to remote logging infrastructure
            console.error(error); // log to console instead
            // TODO: better job of transforming error for user consumption
            this.log(`${operation} failed: ${error.message}`);
            // Let the app keep running by returning an empty result.
            return of(result as T);
        };
    }

    private log(message: string) {
        console.log(message);
    }
}