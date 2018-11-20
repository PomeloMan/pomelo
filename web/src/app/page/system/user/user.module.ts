import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../../../common/module/material.module';
import { UserRoutingModule } from './user-routing.module';

import { UserComponent } from './user.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { UsernameValidatorDirective } from '../../../common/directive/username-validator.directive';

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
	providers: []
})
export class UserModule { }
