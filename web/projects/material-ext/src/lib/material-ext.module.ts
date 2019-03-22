import { NgModule } from '@angular/core';
import { MatExtPopoverModule } from './mat-ext-popover';
import { MatExtButtonModule } from './mat-ext-button';

@NgModule({
  imports: [
    MatExtButtonModule,
    MatExtPopoverModule
  ],
  exports: [
    MatExtButtonModule,
    MatExtPopoverModule
  ]
})
export class MaterialExtModule { }