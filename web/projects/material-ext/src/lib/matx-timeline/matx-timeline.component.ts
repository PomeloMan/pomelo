import { Component, ViewChild, ElementRef, QueryList, ContentChildren, Input } from '@angular/core';
import { MatxTimelineItemComponent } from './matx-timeline-item';

@Component({
  selector: 'matx-timeline',
  templateUrl: './matx-timeline.component.html',
  styleUrls: ['./matx-timeline.component.scss']
})
export class MatxTimelineComponent {

  modes = {
    ul: {
      left: 'matx-timeline-left',
      alternate: 'matx-timeline-alternate',
      right: 'matx-timeline-right'
    },
    li: {
      left: 'matx-timeline-item-left',
      alternate: 'matx-timeline-item-alternate',
      right: 'matx-timeline-item-right'
    }
  }

  @ViewChild('ul') ul: ElementRef;
  @ContentChildren(MatxTimelineItemComponent) matxItems: QueryList<MatxTimelineItemComponent>;

  @Input() mode: string = 'left';// 'left'｜'alternate'｜'right'

  ngAfterContentInit(): void {
    this.matxItems.last.li.nativeElement.classList.add('matx-timeline-item-last');
    this.matxItems.forEach(item => {
      item.li.nativeElement.classList.add(this.modes.li[this.mode]);
    })
  }

  ngOnInit(): void {
    this.ul.nativeElement.classList.add(this.modes.ul[this.mode]);
  }
}
