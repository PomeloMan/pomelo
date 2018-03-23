import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from '../service/auth-guard.service';
import { AuthService } from '../service/auth.service';

import { MainComponent } from './main.component';

const routes: Routes = [
    {
        path: '',
        component: MainComponent,
        children: [
            {
                path: 'dashboard',
                loadChildren: "app/main/dashboard/dashboard.module#DashboardModule"
            },
            {
                path: 'user',
                canActivate: [AuthGuard],
                loadChildren: "app/main/user/user.module#UserModule"
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [
        AuthService,
        AuthGuard
    ]
})
export class MainRoutingModule { }