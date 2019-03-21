import { Component, OnInit, OnDestroy } from '@angular/core';
import * as G2 from '@antv/g2';
import { format, subMinutes } from 'date-fns';

@Component({
  selector: 'app-antv-g2',
  templateUrl: './antv-g2.component.html',
  styleUrls: ['./antv-g2.component.scss']
})
export class AntvG2Component implements OnInit, OnDestroy {

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
      height: window.innerHeight,
      animate: false
    });

    let time = this.now.getTime();
    let lastTime = subMinutes(time, 1).getTime();
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
      alias: '销售额(万)'
    });
    this.chart.line().position('second*value');
    this.chart.point().position('second*value').size(4).shape('circle').style({
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
