import { Component, OnInit, ViewChild, OnDestroy, ElementRef, ViewChildren, QueryList, TemplateRef } from '@angular/core';
import { MatPaginator, MatSort, PageEvent, MatTableDataSource, Sort } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from "@angular/common";
import { Observable } from 'rxjs';

import { BaseComponent } from 'src/app/common/component/base.component';
import { ChButton } from 'src/middleware/ch-button-group/ch-button-group.component';
import { LayoutDirective } from 'src/app/common/directive/layout.directive';

import { MainService } from '../../main.service';
import { UserService, User } from './user.service';

import { FLY_IN_OUT } from 'src/app/common/animations';
import { Page } from 'src/app/config/api';
import { useMockData } from 'src/app/config/app.constant';

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

	@ViewChild(MatPaginator) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;

	@ViewChildren(LayoutDirective) layouts: QueryList<LayoutDirective>;

	layout: TemplateRef<ElementRef>;
	layoutButtons: ChButton[] = LAYOUT_BUTTONS;

	usersSubscribe: Observable<Page<User[]>>;

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
		if (pageEvent)
			super.page(pageEvent);
		this.usersSubscribe = this.service.page(pageEvent);

		this.usersSubscribe.subscribe((page: Page<User[]>) => {
			if (useMockData) {
				this.dataSource = new MatTableDataSource<User>(page.content);
				this.dataSource.paginator = this.paginator;
				this.dataSource.sort = this.sort;
			} else {
				this.dataSource = [];
				this.service.page(null).subscribe((res: Page<User[]>) => {
					this.dataSource = res.content;
					this.length = res.totalElements;
				})
			}
		})
	}

	sortChange(sort: Sort) {
		if (useMockData) {
			const data = this.dataSource.data.slice();
			if (!sort.active || sort.direction === '') {
				this.dataSource.data = data;
				return;
			}

			this.dataSource.data = data.sort((a, b) => {
				const isAsc = sort.direction === 'asc';
				switch (sort.active) {
					case 'number': return this.compare(a.number, b.number, isAsc);
					case 'username': return this.compare(a.username, b.username, isAsc);
					case 'displayName': return this.compare(a.displayName, b.displayName, isAsc);
					case 'email': return this.compare(a.email, b.email, isAsc);
					case 'role': return this.compare(a.role, b.role, isAsc);
					case 'createDate': return this.compare(a.createDate, b.createDate, isAsc);
					default: return 0;
				}
			});
		} else {

		}
	}

	private compare(a: number | string, b: number | string, isAsc: boolean) {
		return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
	}

	/**
	 * 筛选
	 * @param filterValue 
	 */
	applyFilter(filterValue: string) {
		if (useMockData) {
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
			setTimeout(() => {
				this.layout = layoutDirective.template;
			}, 0);
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