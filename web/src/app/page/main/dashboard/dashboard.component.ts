import { Component, OnInit } from '@angular/core';
import { subMinutes, format, subDays } from 'date-fns';

import * as G2 from '@antv/g2';
import * as F2 from '@antv/f2';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  now: Date = new Date();

  private g2: any = new Object();
  private f2: any = new Object();

  current: any = {
    visitors: 0
  }

  constructor(
  ) { }

  ngOnInit() {
    this.render();
  }

  ngAfterViewInit(): void {
  }

  ngOnDestroy(): void {
    this.destory();
  }

  render() {
    this.renderVisitors();
    this.renderVisitorsHistory();
  }

  destory() {
    if (this.f2.interval) {
      this.f2.interval.forEach(intl => {
        clearInterval(intl);
      });
    }
  }

  renderVisitorsHistory() {
    this.f2.barChart = new F2.Chart({
      id: 'BarChart',
      width: 150,
      height: 96,
      animate: true
    })

    let time = this.now.getTime();
    let lastTime = subDays(time, 7).getTime();
    let data = [];
    while (time > lastTime) {
      let day = format(lastTime, 'YYYY-MM-DD');
      data.push({
        day: day,
        value: Math.random()
      })
      lastTime += 86400000;// 1 day
    }
    this.f2.barChart.source(data);

    this.f2.barChart.axis(false)
    this.f2.barChart.interval().position('day*value');
    this.f2.barChart.render();
  }

  renderVisitors() {
    this.f2.areaChart = new F2.Chart({
      id: 'AreaChart',
      width: 150,
      height: 96,
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
    // this.current.visitors = (data[data.length - 1].value) * 1000;
    this.f2.areaChart.source(data);
    this.f2.areaChart.tooltip({
      showCrosshairs: true
    });
    this.f2.areaChart.axis(false)

    this.f2.areaChart.area().position('second*value').shape('smooth');
    this.f2.areaChart.line().position('second*value').shape('smooth');
    this.f2.areaChart.render();

    if (!this.f2.interval) {
      this.f2.interval = new Array();
    }
    let intl = setInterval(() => {
      const _data = data;
      let second = format(time, 'mm:ss');
      _data.shift();
      _data.push({
        second: second,
        value: Math.random()
      })
      if (time % 3 == 0) {
        this.current.visitors = (_data[_data.length - 1].value) * 1000;
      }
      this.f2.areaChart.changeData(_data);
      time += 1000;
    }, 1000);
    this.f2.interval.push(intl);
  }
}
