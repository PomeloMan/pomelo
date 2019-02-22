import { Component, Input, TemplateRef } from '@angular/core';

@Component({
  selector: 'ch-timeline-item',
  templateUrl: './ch-timeline-item.component.html',
  styleUrls: ['./ch-timeline-item.component.scss']
})
export class ChTimelineItemComponent {

  @Input() color: string;
  @Input() template: string | TemplateRef<void>;

}