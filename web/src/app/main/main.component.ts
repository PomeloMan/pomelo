import { Component, OnInit, HostBinding, HostListener } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { OverlayContainer } from '@angular/cdk/overlay';

import { WindowService } from '../service/window.sevice';

class Theme { color: string; class: string; }

@Component({
    selector: 'app-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

    @HostBinding('class') class = 'default-theme';

    themes: Array<Theme> = [
        // light theme
        { color: 'rgb(63, 81, 181)', class: 'default-theme' },
        { color: 'rgb(103, 58, 183)', class: 'unicorn-deeppurple-amber-theme' },
        // dark theme
        { color: 'rgb(233, 30, 99)', class: 'unicorn-pink-bluegrey-theme' },
        { color: 'rgb(156, 39, 176)', class: 'unicorn-purple-green-theme' }
    ];

    constructor(private overlayContainer: OverlayContainer) {
        overlayContainer.getContainerElement().classList.add(this.class);
    }

    ngOnInit(): void {
    }

    setTheme(theme): void {
        this.overlayContainer.getContainerElement().classList.add(theme);
        this.class = theme;
    }
}