import { Component, OnInit } from '@angular/core';
import { subMinutes, format } from 'date-fns';

import * as G2 from '@antv/g2';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  now: Date = new Date();

  private chart: G2.Chart;
  private _lineInterval: any;

  constructor() { }

  ngOnInit() {
    this.renderLine();
  }

  ngOnDestroy(): void {
    if (this._lineInterval) {
      clearInterval(this._lineInterval);
    }
  }

  renderLine() {
    this.chart = new G2.Chart({
      container: 'line',
      forceFit: true,
      height: 150,
      animate: false
    });

    let time = this.now.getTime();
    let lastTime = subMinutes(time, 0.2).getTime();
    let data = [];
    while (time > lastTime) {
      let second = format(lastTime, 'mm:ss');
      data.push({
        second: second,
        value: Math.random()
      })
      lastTime += 1000;
    }
    this.chart.source(data);
    this.chart.tooltip({
      crosshairs: {
        type: 'cross'
      }
    });
    this.chart.scale('value', {
      ticks: [],
    });
    this.chart.scale('second', {
      ticks: [],
    });
    this.chart.area().position('second*value').shape('smooth');
    this.chart.point().position('second*value').size(1).shape('circle').style({
      stroke: '#fff',
      lineWidth: 1
    });
    this.chart.render();
    this._lineInterval = setInterval(() => {
      const _data = data;
      let second = format(time, 'mm:ss');
      _data.shift();
      _data.push({
        second: second,
        value: Math.random()
      })
      this.chart.changeData(_data);
      time += 1000;
    }, 1000);
  }
}
