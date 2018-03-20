import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';

import { CalendarComponent } from './calendar.component';

@NgModule({
    imports: [
        CommonModule,
        MatNativeDateModule,
        MatDatepickerModule
    ],
    declarations: [CalendarComponent],
    exports: [CalendarComponent],
    providers: []
})
export class CalendarModule { }