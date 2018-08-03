import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../common/module/material.module';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './dashboard.component';

const routes: Routes = [
	{ path: '', component: DashboardComponent }
];

@NgModule({
	imports: [
		CommonModule,
		MaterialModule,
		RouterModule.forChild(routes)
	],
	declarations: [DashboardComponent],
	providers: []
})
export class DashboardModule { }