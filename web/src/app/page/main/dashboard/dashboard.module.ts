import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ViserModule } from 'viser-ng';
import { NgxEchartsModule } from 'ngx-echarts';
import { MaterialModule } from 'src/app/common/module/material.module';
import { CountUpModule } from 'countup.js-angular2';

import { DashboardComponent } from './dashboard.component';
import { AntvG2Component } from './antv-g2/antv-g2.component';
import { ViserNgComponent } from './viser-ng/viser-ng.component';
import { EchartsComponent } from './echarts/echarts.component';
import { DragDropModule } from '@angular/cdk/drag-drop';

const routes = [{
  path: '',
  component: DashboardComponent,
}, {
  // https://github.com/antvis/g2/
  path: 'antv-g2',
  component: AntvG2Component
}, {
  // https://github.com/viserjs/viser
  path: 'viser-ng',
  component: ViserNgComponent
}, {
  // https://github.com/apache/incubator-echarts
  path: 'echarts',
  component: EchartsComponent
}]

@NgModule({
  declarations: [
    DashboardComponent,
    AntvG2Component,
    ViserNgComponent,
    EchartsComponent
  ],
  imports: [
    CommonModule,
    ViserModule,
    NgxEchartsModule,
    MaterialModule,
    CountUpModule,
    DragDropModule,
    RouterModule.forChild(routes)
  ]
})
export class DashboardModule { }
