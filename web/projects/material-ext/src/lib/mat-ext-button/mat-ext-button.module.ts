import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule, MatRippleModule, MatProgressSpinnerModule } from '@angular/material';
import { MatExtButtonComponent } from './mat-ext-button.component';

@NgModule({
  declarations: [
    MatExtButtonComponent
  ],
  exports: [
    MatExtButtonComponent
  ],
  imports: [
    CommonModule,
    MatRippleModule,
    MatCommonModule,
    MatProgressSpinnerModule
  ]
})
export class MatExtButtonModule { }
