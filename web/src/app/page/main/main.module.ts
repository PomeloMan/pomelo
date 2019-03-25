import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../../common/module/material.module';
import { MainRoutingModule } from './main-routing.module';

import { MainComponent } from './main.component';
import { FormsModule } from '@angular/forms';
import { ProjectComponent } from './project/project.component';
import { MainService } from './main.service';
import { MaterialExtModule } from 'projects/material-ext/src/lib/material-ext.module';

@NgModule({
	imports: [
		CommonModule,
		FormsModule,
		MaterialModule,
		MainRoutingModule,
		MaterialExtModule
	],
	declarations: [
		MainComponent,
		ProjectComponent
	],
	providers: [
		MainService
	]
})
export class MainModule { }