import { Injectable } from '@angular/core';
import {
	CanActivate,
	Router,
	ActivatedRouteSnapshot,
	RouterStateSnapshot,
	CanActivateChild,
	NavigationExtras,
	CanLoad,
	Route
} from '@angular/router';
import { AuthService } from './auth.service';

/**
 * 路由守卫
 * ref => https://angular.cn/guide/router#milestone-5-route-guards
 */
@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild, CanLoad {

	constructor(
		private authService: AuthService,
		private router: Router
	) { }

	// 没有权限依然会加载模块
	canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
		let url: string = state.url;
		return this.checkLogin(url);
	}

	// 会在任何子路由被激活之前运行
	canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
		return this.canActivate(route, state);
	}

	// 没有权限不会加载模块（会阻塞预加载）
	canLoad(route: Route): boolean {
		let url = `/${route.path}`;
		return this.checkLogin(url);
	}

	checkLogin(url: string): boolean {
		if (this.authService.getAuthorizationToken()) { return true; }
		// Store the attempted URL for redirecting
		this.authService.redirectUrl = url;
		// Create a dummy session id
		let sessionId = Math.random();
		// Set our navigation extras object
		// that contains our global query params and fragment
		let navigationExtras: NavigationExtras = {
			queryParams: { 'session_id': sessionId },
			fragment: 'anchor'
		};
		// Navigate to the login page with extras
		this.router.navigate(['/login'], navigationExtras);
		return false;
	}
}