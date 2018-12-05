import { Router, ActivatedRoute } from "@angular/router";
import { Location } from "@angular/common";
import { isNullOrUndefined } from "util";
import { Navigator } from "../interface/navigator.interface";
import { Paginator } from "../interface/paginator.interface";
import { PageEvent } from "@angular/material";

export class BaseComponent implements Navigator, Paginator {

	// MatPaginator Inputs
	length: number;
	pageIndex: number = 0;// mat-paginator default value
	pageSize: number = 10;
	pageSizeOptions: number[] = [5, 10, 25, 50, 100];

	constructor(
		protected router: Router,
		protected route: ActivatedRoute,
		protected location: Location
	) { }

	/**
	 * @param path '/main/...'
	 * @param children [id, name]
	 * @param queryParams {id:id, name:name}
	 */
	navigate(path, children?, queryParams?) {
		let commands = [];
		commands.push(path);
		if (!isNullOrUndefined(children)) {
			if (typeof children == 'object')
				children.forEach(child => { commands.push(child); });
			else
				commands.push(children);
		}
		this.router.navigate(commands, {
			relativeTo: this.route,
			queryParams: queryParams
		});
	}

	back() {
		this.location.back();
	}

	page(pageEvent: PageEvent) {
		this.length = pageEvent.length;
		this.pageIndex = pageEvent.pageIndex;
		this.pageSize = pageEvent.pageSize;
	}
}