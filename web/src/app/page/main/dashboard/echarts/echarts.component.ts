import { Component, OnInit } from '@angular/core';
import * as echarts from 'echarts';
import 'echarts/theme/macarons.js';

@Component({
  selector: 'app-echarts',
  templateUrl: './echarts.component.html',
  styleUrls: ['./echarts.component.scss']
})
export class EchartsComponent implements OnInit {

  chartOption: any;
  chart: echarts.ECharts;

  constructor() {
  }

  ngOnInit() {
    var el: any = document.getElementById('echart');
    var myChart = echarts.init(el, 'macarons');
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
    // 绘制图表
    myChart.setOption(this.chartOption);
  }
}