import { Component, OnInit } from '@angular/core';
import * as G2 from '@antv/g2';

@Component({
  selector: 'app-antv-g2',
  templateUrl: './antv-g2.component.html',
  styleUrls: ['./antv-g2.component.scss']
})
export class AntvG2Component implements OnInit {

  data = [
    { genre: 'Sports', sold: 275 },
    { genre: 'Strategy', sold: 115 },
    { genre: 'Action', sold: 120 },
    { genre: 'Shooter', sold: 350 },
    { genre: 'Other', sold: 150 }
  ];
  chart: G2.Chart;

  constructor() { }

  ngOnInit() {
    // Step 1: 创建 Chart 对象
    this.chart = new G2.Chart({
      container: 'c1', // 指定图表容器 ID
      width: 600, // 指定图表宽度
      height: 300 // 指定图表高度
    });
    // Step 2: 载入数据源
    this.chart.source(this.data);
    // Step 3：创建图形语法，绘制柱状图，由 genre 和 sold 两个属性决定图形位置，genre 映射至 x 轴，sold 映射至 y 轴
    this.chart.interval().position('genre*sold').color('genre')
    // Step 4: 渲染图表
    this.chart.render();
  }

}
