import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../../common/module/material.module';
import { MainRoutingModule } from './main-routing.module';

import { MainComponent } from './main.component';
import { FormsModule } from '@angular/forms';
import { ProjectComponent } from './project/project.component';
import { PomeloModule } from 'src/app/common/module/pomelo.module';
import { MainService } from './main.service';

@NgModule({
	imports: [
		CommonModule,
		FormsModule,
		MaterialModule,
		MainRoutingModule,
		PomeloModule
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