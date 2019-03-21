import { Component, OnInit } from '@angular/core';
import * as echarts from 'echarts';
import 'echarts/theme/macarons.js';
import { subMinutes, format } from 'date-fns';

@Component({
  selector: 'app-echarts',
  templateUrl: './echarts.component.html',
  styleUrls: ['./echarts.component.scss']
})
export class EchartsComponent implements OnInit {

  now: Date = new Date();

  chartOption: any;

  private lineChart: echarts.ECharts;
  private _lineInterval: any;

  constructor() {
  }

  ngOnInit() {
    this.chartOption = {
      title: {
        text: 'ECharts 入门示例'
      },
      tooltip: {},
      xAxis: {
        data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
      },
      yAxis: {},
      series: [{
        name: '销量',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20]
      }]
    }
    this.renderLine();
  }

  ngOnDestroy(): void {
    if (this._lineInterval) {
      clearInterval(this._lineInterval);
    }
  }

  renderLine() {
    let el: any = document.getElementById('echart');

    let time = this.now.getTime();
    let lastTime = subMinutes(time, 1).getTime();
    let xdata = [];
    let sdata = [];
    while (time > lastTime) {
      let second = format(lastTime, 'mm:ss');
      xdata.push(second);
      sdata.push(Math.random());
      lastTime += 1000;
    }

    let option: echarts.EChartOption = {
      animation: false,
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross'
        }
      },
      xAxis: {
        name: '时间（分：秒）',
        type: 'category',
        data: xdata
      },
      yAxis: {
        name: '销售额（万）',
        type: 'value'
      },
      series: [{
        data: sdata,
        type: 'line'
      }]
    };
    this.lineChart = echarts.init(el, 'macarons', { width: window.innerWidth, height: window.innerHeight });
    this.lineChart.setOption(option);

    this._lineInterval = setInterval(() => {
      let _xdata = xdata;
      let _sdata = sdata;
      let second = format(time, 'mm:ss');
      _xdata.shift();
      _xdata.push(second);
      _sdata.shift();
      _sdata.push(Math.random());

      let _option: any = this.lineChart.getOption();
      _option.xAxis[0].data = _xdata;
      _option.series[0].data = _sdata;
      this.lineChart.setOption(_option);
      time += 1000;
    }, 1000);
  }
}