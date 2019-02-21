import { Component } from '@angular/core';
import { FLY_IN_OUT, toggle } from '../../common/animations';

@Component({
	selector: 'app-dashboard',
	templateUrl: './dashboard.component.html',
	styleUrls: ['./dashboard.component.css'],
	animations: [
		toggle,
		FLY_IN_OUT,
	]
})

export class DashboardComponent {

	tiles = [
		"Simplecard1",
		"Simplecard2",
		"Simplecard3",
		"Simplecard4"
	]

	active: boolean = true;
	state: string = this.active ? 'active' : 'inactive';

	constructor(
	) {
	}

	toggle() {
		this.active = !this.active;
		this.state = this.active ? 'active' : 'inactive';
	}
}