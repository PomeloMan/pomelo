import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatxPopoverComponent } from './matx-popover.component';

@NgModule({
  declarations: [
    MatxPopoverComponent
  ],
  exports: [
    MatxPopoverComponent
  ],
  imports: [
    CommonModule,
    MatButtonModule
  ]
})
export class MatxPopoverModule { }
