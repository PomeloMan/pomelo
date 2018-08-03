import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../common/module/material.module';
import { AuthorityRoutingModule } from './authority-routing.module';

import { AuthorityComponent } from './authority.component';
import { AuthService } from '../../../auth/auth.service';

@NgModule({
	imports: [
		CommonModule,
		MaterialModule,
		AuthorityRoutingModule
	],
	declarations: [
		AuthorityComponent
	],
	providers: [
		AuthService
	]
})
export class AuthorityModule { }
