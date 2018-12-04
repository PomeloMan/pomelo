import { Injectable } from '@angular/core';
import {
	HttpEvent,
	HttpInterceptor,
	HttpHandler,
	HttpRequest,
	HttpResponse
} from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

/**
 * Pass untouched request through to the next request handler.
 */
@Injectable()
export class NoopInterceptor implements HttpInterceptor {

	/**
	 * 数据转换 JSON => URL Encode Params
	 * @param param 
	 * @param key 
	 * @param encode 
	 */
	parse(param, key?, encode?) {
		if (param == null) return '';
		let paramStr = '';
		let t = typeof (param);
		if (t == 'string' || t == 'number' || t == 'boolean') {
			paramStr += '&' + key + '=' + ((encode == null || encode) ? encodeURIComponent(param) : param);
		} else {
			for (let i in param) {
				let k = key == null ? i : key + (param instanceof Array ? '[' + i + ']' : '.' + i);
				paramStr += this.parse(param[i], k, encode);
			}
		}
		return paramStr;
	}

	intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		//do nothing
		return next.handle(req).pipe(
			mergeMap((event: any) => {
				if (event instanceof HttpResponse) {
					if (event.status == 200 && event.body.msg == 'success') {
						return Observable.create(observer => observer.next(event));
					}
					return throwError(event);// {@link src/app/config/api.service.ts handleError()}
				}
				return Observable.create(observer => observer.next(event));
			})
		);
	}
}