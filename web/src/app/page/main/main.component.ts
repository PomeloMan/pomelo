import { Component, OnInit, HostBinding, ViewChild } from '@angular/core';
import { OverlayContainer } from '@angular/cdk/overlay';
import { MatSidenav, MatSidenavContainer } from '@angular/material';
import { SidenavStyle } from './sidenav';
import { Router } from '@angular/router';
import { StorageService } from 'src/app/config/storage.service';
import { STORAGE_SETTING_THEME, STORAGE_SETTING_STYLE } from 'src/app/config/app.constant';

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
	@ViewChild(MatSidenavContainer) sidenavContainer: MatSidenavContainer;

	menus: any[] = [];// 菜单列表
	projects: any[] = [];

	themes: Array<Theme> = [// 主题列表
		// light theme
		{ color: 'rgb(63, 81, 181)', class: 'default-theme' },
		{ color: 'rgb(103, 58, 183)', class: 'deeppurple-amber-theme' },
		// dark theme
		{ color: 'rgb(233, 30, 99)', class: 'pink-bluegrey-theme' },
		{ color: 'rgb(156, 39, 176)', class: 'purple-green-theme' }
	];

	// settings
	private _settings: any[] = SETTINGS_MENUS;
	private _current_theme: Theme;
	private _current_style: SidenavStyle;
	// data
	private _current_project: any;

	constructor(
		private overlayContainer: OverlayContainer,
		private router: Router,
		private storage: StorageService
	) {
		this._current_theme = this.storage.getItem(STORAGE_SETTING_THEME) || this.themes[0];
		this._current_style = this.storage.getItem(STORAGE_SETTING_STYLE) || SidenavStyle.Default;
		this.SidenavStyleArray = Object.keys(this.SidenavStyle).filter((v, i, arr) => i >= arr.length / 2);
		if (!this._current_project) {
			this.router.navigate(['/main/project']);
		}
	}

	ngOnInit(): void {
		this.setTheme(this._current_theme);
		this.initProjects();
		this.initMenus();
	}

	/**
	 * 设置主题
	 * @param theme 
	 */
	setTheme(theme?: Theme): void {
		let _original_theme = this.theme;
		if (theme)
			this.theme = theme.class; // <app-main class="%theme%"> set main theme
		// <div class="cdk-overlay-container"> set overlay theme
		let _cdk_overlay_container_el = this.overlayContainer.getContainerElement();
		if (_cdk_overlay_container_el.classList.contains(_original_theme))
			_cdk_overlay_container_el.classList.replace(_original_theme, this.theme);
		else
			_cdk_overlay_container_el.classList.add(this.theme);
		this.storage.setItem(STORAGE_SETTING_THEME, theme);
	}

	/**
	 * 设置侧边栏样式
	 */
	setStyle(event) {
		this.storage.setItem(STORAGE_SETTING_STYLE, event.value);
	}

	/**
	 * 初始化项目列表
	 */
	initProjects() {
	}

	/**
	 * 初始化菜单列表
	 */
	initMenus() {
		this.menus = menus;
		var result = this.getMenu(this.router.url, this.menus, true);
		if (result) {
			if (result.menu)
				this.navigate(result.menu, result.pmenu);
			else
				this.navigate(result);
		}
	}

	/**
	 * 根据 url 查找 menus 中 link 符合的 menu
	 * @param url 
	 * @param menus 
	 * @param parent 是否返回父级模块
	 */
	getMenu(url?, menus: any[] = [], parent: boolean = false) {
		if (url) {
			let target;
			//every: 碰到return false的时候，循环中止
			//some: 碰到return ture的时候，循环中止
			menus.some(menu => {
				// console.log(menu.name)
				if (menu.children) {
					target = this.getMenu(url, menu.children);
					if (!target) return false;
					if (parent)
						return { 'menu': target, 'pmenu': menu };
					else
						return target;
				} else {
					if (url == menu.link)
						return target = menu;
				}
			})
			return target;
		} else {
			// if url is empty, return all menus(include submenus)
			let _menus = [];
			menus.forEach(menu => {
				_menus.push(menu);
				if (menu.children)
					_menus = _menus.concat(this.getMenu(url, menu.children));
			})
			return _menus;
		}
	}

	navigate(menu, pmenu?) {
		this.getMenu(undefined, this.menus).forEach(item => { item.active = false });
		if (pmenu) pmenu.active = true;
		if (menu)
			menu.active = true;
		else
			this.router.navigate(['/main/project']);
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
	{
		name: 'User Management', link: '', icon: 'supervisor_account',
		children: [
			{ name: 'Dashboard', link: '/main/system/user1', icon: 'account_circle' },
			{ name: 'User ProfileUser Profile', link: '/main/dashboard2', icon: 'face' }
		]
	},
	{ name: 'User Profile', link: '/main/dashboard3', icon: 'face' },
]
const SETTINGS_MENUS = [
	{ name: 'User Profile', link: '', icon: 'assignment_ind' },
	{ name: 'Theme', link: 'ThemeMenu', icon: 'format_color_fill' },
	{ name: 'Sidebar Style', link: 'SidenavStyleMenu', icon: 'style' }
]