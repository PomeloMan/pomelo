import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExampleComponent, ExampleChildComponent } from './example.component';
import { DragDropComponent } from './drag-drop/drag-drop.component';
import { ExampleRoutingModule } from './example-routing.module';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { ChTimelineModule } from 'src/middleware/ch-timeline/ch-timeline.module';

@NgModule({
  declarations: [
    ExampleComponent,
    ExampleChildComponent,
    DragDropComponent
  ],
  imports: [
    CommonModule,
    DragDropModule,
    ChTimelineModule,
    ExampleRoutingModule
  ]
})
export class ExampleModule { }
