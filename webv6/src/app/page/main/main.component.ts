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

    menus: any = [];//菜单

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
        this.initMenus();
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
     * 初始化模块
     */
    initMenus() {
        this.menus = menus;
    }

    /**
     * 滚动条事件
     * @param $event
     */
    private onScroll($event: Event): void {
        console.log($event.srcElement.scrollLeft, $event.srcElement.scrollTop);
        let target: any = $event.target;
        let content = target.children[1];
        if (content.tagName == 'APP-USER-DETAIL') {
            let el: any = document.getElementsByClassName('card-header');
            if ($event.srcElement.scrollTop >= 24) {
                el[0].classList.add('scrolling');
            } else {
                el[0].classList.remove('scrolling');
            }
        }
    };
}

const menus = [
    { name: 'Dashboard', link: '/main/dashboard', icon: 'dashboard' },
    {
        name: 'User Management', link: '', icon: 'supervisor_account',
        children: [
            { name: 'Users', link: '/main/system/user', icon: 'account_circle' },
            { name: 'Security', link: '/main/system/authority', icon: 'security' }
        ]
    },

    { name: 'User Profile', link: '/main/dashboard', icon: 'face' },
]