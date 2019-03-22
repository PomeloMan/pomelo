import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/common/module/material.module';
import { MatExtPopoverComponent } from './mat-ext-popover.component';

@NgModule({
  declarations: [MatExtPopoverComponent],
  exports: [MatExtPopoverComponent],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class MatExtPopoverModule { }
