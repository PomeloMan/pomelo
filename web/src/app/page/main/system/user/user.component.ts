import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, PageEvent, MatTableDataSource, Sort } from '@angular/material';
import { FLY_IN_OUT } from '../../../../common/animations';
import { UserService, User } from './user.service';
import { Page } from 'src/app/model/page';
import { BaseComponent } from 'src/app/common/component/base.component';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from "@angular/common";

import page from '../../../../../assets/mock/user/page.json';
import { of } from 'rxjs';

@Component({
	selector: 'app-user',
	templateUrl: './user.component.html',
	styleUrls: ['./user.component.scss'],
	animations: [
		FLY_IN_OUT
	]
})
export class UserComponent extends BaseComponent implements OnInit {

	displayedColumns: string[] = ['number', 'username', 'displayName', 'role', 'createDate', 'operation'];
	dataSource: any;
	useMockData: boolean = true;

	@ViewChild(MatPaginator) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;

	constructor(
		protected router: Router,
		protected route: ActivatedRoute,
		protected location: Location,
		private service: UserService
	) {
		super(router, route, location);
	}

	ngOnInit() {
		if (this.useMockData) {
			this.dataSource = new MatTableDataSource<User>();
			this.dataSource.paginator = this.paginator;
			this.dataSource.sort = this.sort;
		} else {
			this.dataSource = [];
		}
		this.page({ pageIndex: this.pageIndex, pageSize: this.pageSize, length: this.length });
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
			return of(page).subscribe((res: Page) => {
				this.dataSource.data = res.content;
			});
		} else {
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
}
