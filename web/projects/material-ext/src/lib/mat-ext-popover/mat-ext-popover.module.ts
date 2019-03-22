import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatExtPopoverComponent } from './mat-ext-popover.component';

@NgModule({
  declarations: [MatExtPopoverComponent],
  exports: [MatExtPopoverComponent],
  imports: [
    CommonModule,
    MatButtonModule
  ]
})
export class MatExtPopoverModule { }
