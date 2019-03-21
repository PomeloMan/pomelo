import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-viser-ng',
  templateUrl: './viser-ng.component.html',
  styleUrls: ['./viser-ng.component.scss']
})
export class ViserNgComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    setInterval(() => {
      const data = this.data.slice();
      var now = new Date();
      var time = now.getTime();
      var value1 = ~~30 + Math.random() * 50;
      var direction = Math.random() > 0.5 ? 1 : -1;
      var value2 = value1 + Math.random() * 20 * direction;
      if (data.length >= 200) {
        data.shift();
        data.shift();
      }
      data.push({
        time: time,
        value: value2,
        type: 'yesterday',
      });
      data.push({
        time: time,
        value: value1,
        type: 'today',
      });

      if (data.length > 20) {
        data.shift();
        data.shift();
      }
      this.data = data;
    }, 1000);
  }


  forceFit: boolean = true;
  height: number = 440;
  data = [];
  scale = scale;
  padding = [10, 100, 50, 50];
  lineAnimate = {
    update: {
      duration: 0,
    },
  };
  guide1Opts = {
    lineStyle: {
      stroke: '#F5222D',
      lineWidth: 2,
    },
    text: {
      content: '预警线',
      position: 'start',
      offsetX: 20,
      offsetY: -5,
    },
  };
  dataMarkerOpts = {
    style: {
      text: {
        fontSize: 13,
      },
      point: {
        stroke: '#606060',
      },
    },
  };
  getDataMarkerOptsposition = data => {
    var obj = findMax(data);
    if (obj) {
      return [obj.time, obj.value];
    }
    return [0, 0];
  };
}

function findMax(data) {
  var maxValue = 0;
  var maxObj = null;
  for (var i = 0; i < data.length; i++) {
    var d = data[i];
    if (d.value > maxValue /* && d.type === 'today'*/) {
      maxValue = d.value;
      maxObj = d;
    }
  }
  return maxObj;
}

const scale = [
  {
    dataKey: 'time',
    alias: '时间',
    type: 'time',
    mask: 'MM:ss',
    nice: false,
  },
  {
    dataKey: 'value',
    alias: '占用率',
    min: 0,
    max: 120,
  },
  {
    dataKey: 'type',
    type: 'cat',
  },
];