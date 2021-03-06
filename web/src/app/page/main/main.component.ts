import { Component, OnInit, HostBinding, ViewChild } from '@angular/core';
import { OverlayContainer } from '@angular/cdk/overlay';
import { MatSidenav, MatSidenavContainer, MatMenu } from '@angular/material';
import { Router } from '@angular/router';
import { trigger, style, state, transition, animate } from '@angular/animations';

import { STORAGE_SETTING_THEME, STORAGE_SETTING_STYLE } from 'src/app/config/app.constant';

import { StorageService } from 'src/app/common/service/storage.service';
import { InteractionService } from 'src/app/common/service/Interaction.service';
import { Theme, SidenavStyle, MainService, SettingMenu } from './main.service';
import { AuthService } from 'src/app/config/security/auth.service';
import { User } from './system/user/user.service';

export const ACCORDION = trigger('accordion', [
	state('active', style({ maxHeight: '400px' })),
	transition('void => active', [
		animate('1s 0s ease')
	])
])

@Component({
	selector: 'app-main',
	templateUrl: './main.component.html',
	styleUrls: ['./main.component.scss'],
	animations: [
		ACCORDION
	]
})
export class MainComponent implements OnInit {

	SidenavStyle = SidenavStyle;// Store a reference to the enum '{0: "Default", 1: "Mini", 2: "Slim", Default: 0, Mini: 1, Slim: 2}'
	SidenavStyleArray: any[];

	@HostBinding('class') theme = 'default-theme';
	@ViewChild(MatSidenav) matSidenav: MatSidenav;
	@ViewChild(MatSidenavContainer) sidenavContainer: MatSidenavContainer;


	@ViewChild('ThemeMenu') themeMenu: MatMenu;
	@ViewChild('SidenavStyleMenu') sidenavStyleMenu: MatMenu;


	isFullscreen: boolean = false;

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
	_current_style: SidenavStyle;
	_current_user: User;
	_settings_menus: any[] = SETTINGS_MENUS;
	private _settings_menus_map;
	private _current_theme: Theme;
	// data
	private _current_project: any;

	constructor(
		public router: Router,
		private overlayContainer: OverlayContainer,
		private service: MainService,
		private authService: AuthService,
		private storage: StorageService,
		private interactionService: InteractionService
	) {
		this._current_theme = this.storage.getItem(STORAGE_SETTING_THEME) || this.themes[0];
		this._current_style = this.storage.getItem(STORAGE_SETTING_STYLE) || SidenavStyle.Default;
		this._current_user = this.authService.getCurrentUser();
		this.SidenavStyleArray = Object.keys(this.SidenavStyle).filter((v, i, arr) => i >= arr.length / 2);
	}

	hasChildToolbar: boolean = false;
	ngOnInit(): void {
		this.initSettingMenus();

		let _this = this;
		this.service.change$.subscribe((res: any) => {
			setTimeout(() => {
				_this.hasChildToolbar = res.hasChildToolbar;
			}, 0);
		})

		// if (!this._current_project) {
		// 	this.router.navigate(['/main/project']);
		// }

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
		if (menu.active) {
			menu.active = !menu.active;
			return;
		}
		this.getMenu(undefined, this.menus).forEach(item => { item.active = false });
		if (pmenu) pmenu.active = true;
		if (menu) {
			menu.active = true;
		} else {
			this.router.navigate(['/main/project']);
		}
	}


	private signout() {
		this.authService.clear();
		this.router.navigate(['/login']);
	}

	private initSettingMenus() {
		let $this = this;
		this._settings_menus_map = {
			'theme': {
				ref: this.themeMenu
			},
			'style': {
				ref: this.sidenavStyleMenu
			},
			'profile': {
				fns: () => {
					$this.router.navigate(['/main/system/user/detail', $this._current_user.username]);
				}
			},
			'signout': {
				fns: () => {
					$this.signout();
				}
			}
		}
		this._settings_menus.forEach(menu => {
			if (menu.type === 'button') {
				menu.fns = this._settings_menus_map[menu.id].fns;
			} else if (menu.type === 'link') {
				menu.ref = this._settings_menus_map[menu.id].ref;
			}
		})
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
		name: 'Charts', link: '', icon: 'bubble_chart',
		children: [
			{ name: 'AntV-G2', link: '/main/dashboard/antv-g2', icon: 'show_chart' },
			{ name: 'ECharts', link: '/main/dashboard/echarts', icon: 'insert_chart' },
			{ name: 'Viser', link: '/main/dashboard/viser-ng', icon: 'pie_chart' }
		]
	},
	{ name: 'User Profile', link: '/main/dashboard3', icon: 'face' },
]

const SETTINGS_MENUS: SettingMenu[] = [
	{ id: 'profile', name: 'User Profile', type: 'button', link: '', icon: 'assignment_ind' },
	{ id: 'theme', name: 'Theme', type: 'link', link: 'ThemeMenu', icon: 'format_color_fill' },
	{ id: 'style', name: 'Sidebar Style', type: 'link', link: 'SidenavStyleMenu', icon: 'style' },
	{ id: 'signout', name: 'Sign out', type: 'button', link: '', icon: 'directions_run' }
]