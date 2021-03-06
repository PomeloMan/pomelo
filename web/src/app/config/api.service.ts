import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { isNullOrUndefined } from 'util';
import { BASE_PATH } from './app.constant';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  base: any = BASE_PATH;
  retry: number = 1;

  defaultOptions: any = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  constructor(
    private http: HttpClient
    // private native: NativeService
  ) { }

  get(url: string, options?: any): Observable<any> {
    let requestUrl = url;
    if (isNullOrUndefined(options)) options = this.defaultOptions;
    if (!url.startsWith('http')) requestUrl = this.base + url;
    return this.http.get(requestUrl, options)
      .pipe(
        retry(this.retry),
        // {@link noop-interceptor.ts}
        // mergeMap(res => {
        //   _this.native.openSnackBar(res.body.msg, 'ok');
        //   return throwError(event);
        //   return Observable.create(observer => observer.next(event));
        // }),
        catchError(this.handleError)
      );
  }

  post(url: string, body: any, options?: any): Observable<any> {
    let requestUrl = url;
    if (isNullOrUndefined(options)) options = this.defaultOptions;
    if (!url.startsWith('http')) requestUrl = this.base + url;
    return this.http.post(requestUrl, body, options)
      .pipe(
        retry(this.retry),
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${JSON.stringify(error.error)}`);
    }
    console.error(error);
    // return an observable with a user-facing error message
    return throwError('Something bad happened; please try again later.');
  }
}