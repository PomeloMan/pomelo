import { Component, OnInit, Input, Output, EventEmitter, ElementRef } from '@angular/core';

@Component({
  selector: 'ch-button-group',
  templateUrl: './ch-button-group.component.html',
  styleUrls: ['./ch-button-group.component.scss']
})
export class ChButtonGroupComponent implements OnInit {

  checked: ChButton;

  @Input() items: ChButton[];
  @Output() change: EventEmitter<ChButton> = new EventEmitter();

  ngOnInit() {
    this.checked = this.items[0];
  }

  select(item: ChButton) {
    this.checked = item;
    this.change.emit(item);
  }
}

export interface ChButton {
  id: string;
  icon?: string;
  text: string;
  disabled?: boolean;
}