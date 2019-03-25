import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule, MatRippleModule, MatProgressSpinnerModule } from '@angular/material';
import { MatxButtonComponent } from './matx-button.component';

@NgModule({
  declarations: [
    MatxButtonComponent
  ],
  exports: [
    MatxButtonComponent
  ],
  imports: [
    CommonModule,
    MatRippleModule,
    MatCommonModule,
    MatProgressSpinnerModule
  ]
})
export class MatxButtonModule { }
