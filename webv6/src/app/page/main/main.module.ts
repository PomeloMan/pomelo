import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../../common/module/material.module';
import { MainRoutingModule } from './main-routing.module';

import { MainComponent } from './main.component';

@NgModule({
	imports: [
		CommonModule,
		MaterialModule,
		MainRoutingModule
	],
	declarations: [
		MainComponent
	],
	providers: []
})
export class MainModule { }