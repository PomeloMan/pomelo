import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../../../../common/module/material.module';
import { UserRoutingModule } from './user-routing.module';

import { UserComponent } from './user.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { UsernameValidatorDirective } from '../../../../common/directive/username-validator.directive';
import { UserService } from './user.service';
import { ChModule } from 'src/middleware/ch.module';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MaterialExtModule } from 'projects/material-ext/src/lib/material-ext.module';

import { LayoutDirective } from 'src/app/common/directive/layout.directive';
import { HoverClassDirective } from 'src/app/common/directive/hover-class.directive';

import { UserEditComponent } from './user-edit/user-edit.component';

@NgModule({
	imports: [
		CommonModule,
		FormsModule,
		ReactiveFormsModule,//FormControl
		MaterialModule,
		ChModule,
		FontAwesomeModule,
		MaterialExtModule,
		UserRoutingModule
	],
	declarations: [
		UserComponent,
		UserDetailComponent,
		LayoutDirective,
		UserEditComponent,
		UsernameValidatorDirective,
		HoverClassDirective
	],
	providers: [
		UserService
	]
})
export class UserModule { }
