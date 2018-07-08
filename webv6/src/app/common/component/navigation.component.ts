import { Router, ActivatedRoute } from "@angular/router";
import { Location } from "@angular/common";

export class NavigationComponent {

    constructor(
        public router: Router,
        public route: ActivatedRoute,
        public location: Location
    ) { }

    navigate(path, child?) {
        if (child) {
            this.router.navigate([path, child], {
                relativeTo: this.route, queryParams: {
                    type: 2
                }
            });
        } else {
            this.router.navigate([path], {
                relativeTo: this.route, queryParams: {
                    type: 2
                }
            });
        }
    }
}