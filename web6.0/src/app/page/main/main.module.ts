import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';

import { MainRoutingModule } from './main-routing.module';

import { MainComponent } from './main.component';

@NgModule({
    imports: [
        CommonModule,
        MatMenuModule,
        MatToolbarModule,
        MatSidenavModule,
        MatButtonModule,
        MatListModule,
        MatGridListModule,
        MatIconModule,
        MatTooltipModule,
        MainRoutingModule
    ],
    declarations: [MainComponent],
    providers: []
})
export class MainModule { }