import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';

import { DashboardComponent } from './dashboard.component';

@NgModule({
    imports: [
        CommonModule,
        MatGridListModule,
        MatCardModule,
        MatDatepickerModule
    ],
    declarations: [DashboardComponent],
    providers: []
})
export class DashboardModule { }