import { Injectable } from '@angular/core';
import { ApiService } from '../../../../config/api.service';

import { Observable, of } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class AuthorityService {

	constructor(
		public service: ApiService
	) { }

	getAuthorities(sort: string, order: string, page: number): Observable<Authority[]> {
		return of([]);
	}
}

export class Authority {
	id: number;
	name: string;
	createdDate: number;
}