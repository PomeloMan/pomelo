import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../common/module/material.module';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './dashboard.component';

import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { FormsModule } from '@angular/forms';

const routes: Routes = [
	{ path: '', component: DashboardComponent }
];

@NgModule({
	imports: [
		CommonModule,
		FormsModule,
		MaterialModule,
		CalendarModule.forRoot({
			provide: DateAdapter,
			useFactory: adapterFactory
		  }),
		RouterModule.forChild(routes)
	],
	declarations: [DashboardComponent],
	providers: []
})
export class DashboardModule { }