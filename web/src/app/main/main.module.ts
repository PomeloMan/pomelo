import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatGridListModule } from '@angular/material/grid-list';

import { MainRoutingModule } from './main-routing.module';
import { CalendarModule } from '../common/calendar/calendar.module';
import { DashboardModule } from './dashboard/dashboard.module';

import { MainComponent } from './main.component';
import { LoginComponent } from '../login/login.component';
import { CalendarComponent } from '../common/calendar/calendar.component';

import { UserService } from '../user/user.service';
import { WindowService } from '../service/window.sevice';

@NgModule({
    imports: [
        CommonModule,
        MainRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        MatToolbarModule,
        MatSidenavModule,
        MatFormFieldModule,
        MatCheckboxModule,
        MatInputModule,
        MatButtonModule,
        MatListModule,
        MatIconModule,
        MatTooltipModule,
        MatMenuModule,
        MatGridListModule,
        CalendarModule,
        DashboardModule
    ],
    declarations: [MainComponent, LoginComponent],
    providers: [UserService, WindowService]
})
export class MainModule { }