import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../../common/module/material.module';
import { MainRoutingModule } from './main-routing.module';

import { MainComponent } from './main.component';
import { FormsModule } from '@angular/forms';

@NgModule({
	imports: [
		CommonModule,
		FormsModule,
		MaterialModule,
		MainRoutingModule
	],
	declarations: [
		MainComponent
	],
	providers: []
})
export class MainModule { }