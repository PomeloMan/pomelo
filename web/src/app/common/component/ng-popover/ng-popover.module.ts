import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgPopoverComponent } from './ng-popover.component';
import { MaterialModule } from '../../module/material.module';

@NgModule({
  declarations: [
    NgPopoverComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    NgPopoverComponent
  ]
})
export class NgPopoverModule { }
