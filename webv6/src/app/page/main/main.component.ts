import { Component, OnInit, HostBinding, ViewChild } from '@angular/core';
import { OverlayContainer } from '@angular/cdk/overlay';
import { MatSidenav } from '@angular/material';
import { SidenavStyle } from './sidenav';
import { Router, ActivatedRoute } from '@angular/router';

class Theme { color: string; class: string; }

@Component({
	selector: 'app-main',
	templateUrl: './main.component.html',
	styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

	SidenavStyle = SidenavStyle;// Store a reference to the enum '{0: "Default", 1: "Mini", 2: "Slim", Default: 0, Mini: 1, Slim: 2}'
	SidenavStyleArray: any[];

	@HostBinding('class') theme = 'default-theme';
	@ViewChild(MatSidenav) matSidenav: MatSidenav;

	menus: any = [];// 菜单列表

	sidenavStyle: SidenavStyle = SidenavStyle.Default;// 侧菜单栏
	themes: Array<Theme> = [// 主题列表
		// light theme
		{ color: 'rgb(63, 81, 181)', class: 'default-theme' },
		{ color: 'rgb(103, 58, 183)', class: 'deeppurple-amber-theme' },
		// dark theme
		{ color: 'rgb(233, 30, 99)', class: 'pink-bluegrey-theme' },
		{ color: 'rgb(156, 39, 176)', class: 'purple-green-theme' }
	];

	constructor(
		private overlayContainer: OverlayContainer,
		private router: Router
	) {
		this.setTheme();
		this.SidenavStyleArray = Object.keys(this.SidenavStyle).filter((v, i, arr) => i >= arr.length / 2);
	}

	ngOnInit(): void {
		this.initMenus();
	}

	/**
	 * 设置主题
	 * @param theme 
	 */
	setTheme(theme?): void {
		var _original_theme = this.theme;
		if (theme)
			this.theme = theme; // <app-main class="%theme%"> set main theme
		// <div class="cdk-overlay-container"> set overlay theme
		var _cdk_overlay_container_el = this.overlayContainer.getContainerElement();
		if (_cdk_overlay_container_el.classList.contains(_original_theme))
			_cdk_overlay_container_el.classList.replace(_original_theme, this.theme);
		else
			_cdk_overlay_container_el.classList.add(this.theme);
	}

	/**
	 * 初始化菜单列表
	 */
	initMenus() {
		this.menus = menus;
		var menu = this.getMenu(this.router.url, this.menus, true);
		if (menu)
			menu.active = true;
	}

	/**
	 * 根据 url 查找 menus 中 link 符合的 menu
	 * @param url 
	 * @param menus 
	 * @param parent 是否返回父级模块
	 */
	getMenu(url, menus, parent: boolean = false) {
		let target;
		//every: 碰到return false的时候，循环中止
		//some: 碰到return ture的时候，循环中止
		menus.some(menu => {
			// console.log(menu.name)
			if (menu.children) {
				target = this.getMenu(url, menu.children);
				if (target && parent)
					return target = menu;
			} else {
				if (url == menu.link)
					return target = menu;
			}
		})
		return target;
	}

	navigate(menu) {
		this.menus.forEach(item => { item.active = false });
		menu.active = true;
	}
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