import { Router, ActivatedRoute } from "@angular/router";
import { Location } from "@angular/common";
import { isNullOrUndefined } from "util";

export class NavigationComponent {

	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public location: Location
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
}