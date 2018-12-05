import { Component } from '@angular/core';
import { BaseComponent } from '../../common/component/base.component';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
	selector: 'app-not-found',
	templateUrl: './not-found.component.html',
	styleUrls: ['./not-found.component.scss']
})
export class PageNotFoundComponent extends BaseComponent {

	constructor(
		protected router: Router,
		protected route: ActivatedRoute,
		protected location: Location
	) {
		super(router, route, location)
	}

}
