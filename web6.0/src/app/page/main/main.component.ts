import { Component, OnInit, HostBinding } from '@angular/core';
import { OverlayContainer } from '@angular/cdk/overlay';

class Theme { color: string; class: string; }

@Component({
    selector: 'app-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

    @HostBinding('class') class = 'default-theme';

    /**
     * 主题列表
     */
    themes: Array<Theme> = [
        // light theme
        { color: 'rgb(63, 81, 181)', class: 'default-theme' },
        { color: 'rgb(103, 58, 183)', class: 'unicorn-deeppurple-amber-theme' },
        // dark theme
        { color: 'rgb(233, 30, 99)', class: 'unicorn-pink-bluegrey-theme' },
        { color: 'rgb(156, 39, 176)', class: 'unicorn-purple-green-theme' }
    ];

    constructor(
        private overlayContainer: OverlayContainer
    ) {
        this.setTheme();
    }

    ngOnInit(): void {
    }

    /**
     * 设置主题
     * @param theme 
     */
    setTheme(theme?): void {
        if (theme) {
            this.class = theme;
        }
        this.overlayContainer.getContainerElement().classList.add(this.class);
    }

    /**
     * 初始化权限模块
     */
    initAuthority() {

    }
}