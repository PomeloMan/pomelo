import { Component, OnInit, HostBinding } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
    selector: 'dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent {

    tiles = [
        "Simplecard1",
        "Simplecard2",
        "Simplecard3",
        "Simplecard4"
    ]
}