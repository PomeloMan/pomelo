import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthorityComponent } from './authority.component';

const routes: Routes = [
    {
        path: '',
        component: AuthorityComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AuthorityRoutingModule { }