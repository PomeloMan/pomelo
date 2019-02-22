import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'ch-timeline',
  templateUrl: './ch-timeline.component.html',
  styleUrls: ['./ch-timeline.component.scss']
})
export class ChTimelineComponent {

  @ViewChild('ul') ul: ElementRef;

  ngOnInit(): void {
    let ulElement = this.ul.nativeElement;
    let last = ulElement.children[ulElement.childElementCount - 1];
    last.classList.add('ch-timeline-item-last')
  }
}
