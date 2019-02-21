import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../../../../common/module/material.module';
import { UserRoutingModule } from './user-routing.module';

import { UserComponent } from './user.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { UsernameValidatorDirective } from '../../../../common/directive/username-validator.directive';
import { UserService } from './user.service';

@NgModule({
	imports: [
		CommonModule,
		FormsModule,
		ReactiveFormsModule,//FormControl
		MaterialModule,
		UserRoutingModule
	],
	declarations: [
		UserComponent,
		UserDetailComponent,
		UsernameValidatorDirective,
	],
	providers: [
		UserService
	]
})
export class UserModule { }
