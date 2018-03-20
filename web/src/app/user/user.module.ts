import { NgModule } from '@angular/core';

import { UserComponent } from './user.component';
import { UserRoutingModule } from './user-routing.module';
import { UserService } from './user.service';

@NgModule({
    imports: [UserRoutingModule],
    declarations: [UserComponent],
    providers: [UserService]
})
export class UserModule { }