import { NgModule } from '@angular/core';
import { MatxPopoverModule } from './matx-popover';
import { MatxButtonModule } from './matx-button';
import { MatxTimelineModule } from './matx-timeline';

@NgModule({
  imports: [
    MatxButtonModule,
    MatxPopoverModule,
    MatxTimelineModule
  ],
  exports: [
    MatxButtonModule,
    MatxPopoverModule,
    MatxTimelineModule
  ]
})
export class MaterialExtModule { }