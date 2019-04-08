import { Component, OnInit, ViewChild, OnDestroy, ElementRef, ViewChildren, QueryList, TemplateRef } from '@angular/core';
import { MatPaginator, MatSort, PageEvent, MatTableDataSource, Sort } from '@angular/material';
import { FLY_IN_OUT } from '../../../../common/animations';
import { UserService, User } from './user.service';
import { Page } from 'src/app/model/page';
import { BaseComponent } from 'src/app/common/component/base.component';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from "@angular/common";

import page from '../../../../../assets/mock/user/page.json';
import { ChButton } from 'src/middleware/ch-button-group/ch-button-group.component';
import { MainService } from '../../main.service';
import { LayoutDirective } from 'src/app/common/directive/layout.directive';

@Component({
	selector: 'app-user',
	templateUrl: './user.component.html',
	styleUrls: ['./user.component.scss'],
	animations: [
		FLY_IN_OUT
	]
})
export class UserComponent extends BaseComponent implements OnInit, OnDestroy {

	displayedColumns: string[] = ['number', 'username', 'displayName', 'email', 'role', 'createDate', 'operation'];
	dataSource: any;
	useMockData: boolean = true;

	@ViewChild(MatPaginator) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;

	@ViewChildren(LayoutDirective) layouts: QueryList<LayoutDirective>;

	layout: TemplateRef<ElementRef>;
	layoutButtons: ChButton[] = LAYOUT_BUTTONS;

	constructor(
		protected router: Router,
		protected route: ActivatedRoute,
		protected location: Location,
		private service: UserService,
		private mainService: MainService
	) {
		super(router, route, location);
	}

	ngOnInit() {
		this.mainService.change({ hasChildToolbar: true });
	}

	ngAfterViewInit(): void {
		this.changeLayout(this.layoutButtons[0]);
	}

	ngOnDestroy(): void {
		this.mainService.change({ hasChildToolbar: false });
	}

	page(pageEvent?: PageEvent) {
		if (pageEvent) super.page(pageEvent);
		this.getUsers();
	}

	sortChange(sortEvent: Sort) {
		this.getUsers();
	}

	getUsers() {
		if (this.useMockData) {
			this.dataSource = new MatTableDataSource<User>(page.content);
			this.dataSource.paginator = this.paginator;
			this.dataSource.sort = this.sort;
		} else {
			this.dataSource = [];
			this.service.page(null).subscribe((res: Page) => {
				this.dataSource = res.content;
				this.length = res.totalElements;
			})
		}
	}

	/**
	 * 筛选
	 * @param filterValue 
	 */
	applyFilter(filterValue: string) {
		if (this.useMockData) {
			this.dataSource.filter = filterValue.trim().toLowerCase();
		}
	}

	/**
	 * 更换布局
	 * @param event 
	 */
	changeLayout(event: ChButton) {
		let layoutDirective = this.layouts.toArray().find(l => l.layoutId == event.id);
		if (layoutDirective) {
			this.layout = layoutDirective.template;
			setTimeout(() => {
				this.page();
			}, 0);
		}
	}
}

const LAYOUT_BUTTONS: ChButton[] = [
	{
		id: 'ListLayout',
		icon: 'view_list',
		text: 'List'
	}, {
		id: 'GridLayout',
		icon: 'view_module',
		text: 'Grid',
		// disabled: true
	}
]