import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChTimelineComponent } from './ch-timeline.component';
import { ChTimelineItemComponent } from './ch-timeline-item/ch-timeline-item.component';

@NgModule({
  declarations: [
    ChTimelineComponent,
    ChTimelineItemComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    ChTimelineComponent,
    ChTimelineItemComponent
  ]
})
export class ChTimelineModule { }
