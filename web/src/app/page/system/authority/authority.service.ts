import { Injectable } from '@angular/core';
import { ApiService } from '../../../config/api.service';

import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { authorities } from '../../../data-model/authority';

@Injectable({
	providedIn: 'root'
})
export class AuthorityService {

	constructor(
		public service: ApiService
	) { }

	delayMs = 3000;

	getAuthorities(sort: string, order: string, page: number): Observable<Authority[]> {
		return of(authorities).pipe(delay(this.delayMs));
	}
}

export class Authority {
	id: number;
	name: string;
	createdDate: number;
}