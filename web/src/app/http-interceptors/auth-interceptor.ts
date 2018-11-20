import { Injectable } from '@angular/core';
import {
	HttpInterceptor,
	HttpHandler,
	HttpRequest
} from '@angular/common/http';

import { AuthService } from '../config/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthGuard } from '../config/auth-guard.service';

/**
 * This interceptor is used to add a request header to each request
 */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

	constructor(
		private auth: AuthService,
		private guard: AuthGuard
	) { }

	intercept(req: HttpRequest<any>, next: HttpHandler) {
		if (req.url.endsWith('/login')) {
			return next.handle(req);
		}
		// Get the auth token from the service.
		const authToken = this.auth.getAuthorizationToken();
		/*
		* The verbose way:
		// Clone the request and replace the original headers with cloned headers, updated with the authorization.
		const authReq = req.clone({
			headers: req.headers.set('Authorization', authToken)
		});
		*/
		// Clone the request and set the new header in one step.
		const authReq = req.clone({ setHeaders: { Authorization: authToken } });

		// send cloned request with header to the next handler.
		return next.handle(authReq);
	}
}
