import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MainComponent } from './main.component';
import { AuthGuard } from '../../auth/auth-guard.service';
import { PageNotFoundComponent } from '../not-found/not-found.component';

const routes: Routes = [
    {
        path: '',
        component: MainComponent,
        children: [
            {
                path: 'dashboard',
                loadChildren: './dashboard/dashboard.module#DashboardModule',
            },
            {
                path: 'system/user',
                loadChildren: './system/user/user.module#UserModule'
            },
            {
                path: 'system/authority',
                // canActivate: [AuthGuard],
                loadChildren: './system/authority/authority.module#AuthorityModule'
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: []
})
export class MainRoutingModule { }