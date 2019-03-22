import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material';
import { MatExtButtonComponent } from './mat-ext-button.component';

@NgModule({
  declarations: [MatExtButtonComponent],
  exports: [MatExtButtonComponent],
  imports: [
    CommonModule,
    MatButtonModule
  ]
})
export class MatExtButtonModule { }
