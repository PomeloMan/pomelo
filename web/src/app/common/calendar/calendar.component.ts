import { Component, OnInit, ViewEncapsulation } from '@angular/core';

import { WindowService } from '../../service/window.sevice';

// import * as $ from 'jquery';
import * as moment from 'moment';
// import * as _ from 'underscore';
// import 'clndr';

@Component({
    selector: 'calendar',
    templateUrl: './calendar.component.html',
    styleUrls: ['./calendar.component.css'],
})
export class CalendarComponent {

    window: any;
    options: CalendarOption = {
        weekdays: moment.weekdaysMin(),
        days: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16],
        weeks: null
    };

    constructor(private win: WindowService) {
        this.window = win.nativeWindow;
    }

    getChangedValue($event): void {

    }
}

export interface CalendarOption {
    weekdays: any[]
    days: any[]
    weeks: number
}