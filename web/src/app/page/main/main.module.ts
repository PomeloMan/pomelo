import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../../common/module/material.module';
import { MainRoutingModule } from './main-routing.module';

import { MainComponent } from './main.component';
import { FormsModule } from '@angular/forms';
import { ProjectComponent } from './project/project.component';

@NgModule({
	imports: [
		CommonModule,
		FormsModule,
		MaterialModule,
		MainRoutingModule
	],
	declarations: [
		MainComponent,
		ProjectComponent
	],
	providers: []
})
export class MainModule { }