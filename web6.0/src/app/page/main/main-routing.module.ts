import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MainComponent } from './main.component';
import { AuthGuard } from '../../auth/auth-guard.service';

const routes: Routes = [
    {
        path: '',
        component: MainComponent,
        children: [
            {
                path: 'system/authority',
                loadChildren: "./system/authority/authority.module#AuthorityModule"
            },
            {
                path: 'system/user',
                canActivate: [AuthGuard],
                loadChildren: "./user/user.module#UserModule"
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