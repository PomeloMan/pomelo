import { Component, OnInit, HostBinding } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { flyInOut1, flyInOut2, flyInOutGroup, toggle } from '../../../common/animations';

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css'],
    animations: [
        toggle,
        flyInOut1,
        // flyInOut2,
        // flyInOutGroup
    ]
})

export class DashboardComponent {

    tiles = [
        "Simplecard1",
        "Simplecard2",
        "Simplecard3",
        "Simplecard4"
    ]

    active: boolean = true;
    state: string = this.active ? 'active' : 'inactive';
    toggle() {
        this.active = !this.active;
        this.state = this.active ? 'active' : 'inactive';
    }
}