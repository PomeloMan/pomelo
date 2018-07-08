import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SelectivePreloadingStrategy } from './config/selective-preloading-strategy';
import { PageNotFoundComponent } from './page/not-found/not-found.component';

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
        loadChildren: './page/main/main.module#MainModule'
    },
    {
        path: '**',
        component: PageNotFoundComponent
    }
];

@NgModule({
    imports: [
        RouterModule.forRoot(
            routes,
            {
                preloadingStrategy: SelectivePreloadingStrategy
            }
        )
    ],
    exports: [
        RouterModule
    ],
    providers: [
        SelectivePreloadingStrategy
    ]
})
export class AppRoutingModule { }