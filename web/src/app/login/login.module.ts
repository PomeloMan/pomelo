import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule, MatInputModule, MatButtonModule } from '@angular/material';

import { LoginComponent } from './login.component';
import { LoginRoutingModule } from './login-routing.module';
import { UserService } from '../user/user.service';

@NgModule({
    imports: [
        FormsModule,
        MatInputModule,
        MatFormFieldModule,
        MatButtonModule,
        LoginRoutingModule
    ],
    declarations: [LoginComponent],
    providers: [UserService]
})
export class LoginModule { }