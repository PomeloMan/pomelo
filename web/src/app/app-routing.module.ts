import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PageNotFoundComponent } from './page/not-found/not-found.component';

import { AuthGuard } from './config/security/auth-guard.service';
import { SelectivePreloadingStrategy } from './config/selective-preloading-strategy';

const routes: Routes = [
	{
		path: '',
		redirectTo: '/main',
		pathMatch: 'full'
	},
	{
		path: 'login',
		loadChildren: './page/login/login.module#LoginModule',
		data: { preload: true }
	},
	{
		path: 'main',
		loadChildren: './page/main/main.module#MainModule',
		// canLoad: [AuthGuard],
		data: { preload: true }
	},
	{
		path: '**',
		component: PageNotFoundComponent
	}
];

@NgModule({
	imports: [
		RouterModule.forRoot(routes, {
			preloadingStrategy: SelectivePreloadingStrategy
		})
	],
	exports: [RouterModule],
	providers: [SelectivePreloadingStrategy]
})
export class AppRoutingModule { }