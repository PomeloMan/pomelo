import { Component } from '@angular/core';
import { NavigationComponent } from '../../common/component/navigation.component';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
	selector: 'app-not-found',
	templateUrl: './not-found.component.html',
	styleUrls: ['./not-found.component.scss']
})
export class PageNotFoundComponent extends NavigationComponent {

	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public location: Location
	) {
		super(router, route, location)
	}

}
