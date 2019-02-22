import { Component, OnInit, TemplateRef, Input } from '@angular/core';

@Component({
  selector: 'app-example',
  templateUrl: './example.component.html',
  styleUrls: ['./example.component.css']
})
export class ExampleComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
}
@Component({
  selector: 'app-example-child',
  template: `
    <ng-template [ngTemplateOutlet]="template"></ng-template>
  `,
  styles: [``]
})
export class ExampleChildComponent implements OnInit {

  @Input() template: TemplateRef<void>;

  constructor() { }

  ngOnInit() {
  }

}