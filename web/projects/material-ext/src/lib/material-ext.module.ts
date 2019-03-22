import { NgModule } from '@angular/core';
import { MatExtButtonModule } from './mat-ext-button';
import { MatExtPopoverModule } from './mat-ext-popover';

@NgModule({
  declarations: [],
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

export * from './mat-ext-button';
export * from './mat-ext-popover';