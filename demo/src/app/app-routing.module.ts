import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SelectivePreloadingStrategy } from './service/selective-preloading-strategy';

const routes: Routes = [
    {
        path: '',
        redirectTo: '/main',
        pathMatch: 'full'
    },
    {
        path: 'login',
        loadChildren: 'app/login/login.module#LoginModule',
        data: { preload: true }
    },
    {
        path: 'main',
        loadChildren: 'app/main/main.module#MainModule'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {
        preloadingStrategy: SelectivePreloadingStrategy
    })],
    exports: [RouterModule],
    providers: [
        SelectivePreloadingStrategy
    ]
})
export class AppRoutingModule { }