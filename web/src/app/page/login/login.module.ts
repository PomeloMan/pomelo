import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../../common/module/material.module';
import { RouterModule } from '@angular/router';

import { LoginComponent } from './login.component';

const routes = [
	{ path: '', component: LoginComponent }
]

@NgModule({
	imports: [
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
		MaterialModule,
		RouterModule.forChild(routes)
	],
	declarations: [LoginComponent],
	providers: []
})
export class LoginModule { }