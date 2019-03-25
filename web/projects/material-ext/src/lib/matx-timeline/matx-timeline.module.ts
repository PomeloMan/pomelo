import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatxTimelineComponent } from './matx-timeline.component';
import { MatxTimelineItemComponent } from './matx-timeline-item/matx-timeline-item.component';

@NgModule({
  declarations: [
    MatxTimelineComponent,
    MatxTimelineItemComponent
  ],
  exports: [
    MatxTimelineComponent,
    MatxTimelineItemComponent
  ],
  imports: [
    CommonModule
  ]
})
export class MatxTimelineModule { }
