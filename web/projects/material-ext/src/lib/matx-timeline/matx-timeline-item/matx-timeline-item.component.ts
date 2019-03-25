import { Component, Input, TemplateRef, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'matx-timeline-item',
  templateUrl: './matx-timeline-item.component.html',
  styleUrls: ['./matx-timeline-item.component.scss']
})
export class MatxTimelineItemComponent {

  @Input() color: string;
  @Input() template: string | TemplateRef<void>;

  @ViewChild('li') li: ElementRef;
}
