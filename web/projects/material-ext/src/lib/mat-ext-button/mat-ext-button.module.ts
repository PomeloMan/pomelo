import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/common/module/material.module';
import { MatExtButtonComponent } from './mat-ext-button.component';

@NgModule({
  declarations: [MatExtButtonComponent],
  exports: [MatExtButtonComponent],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class MatExtButtonModule { }
