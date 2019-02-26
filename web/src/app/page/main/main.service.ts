import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable()
export class MainService {

    // Observable string sources
    private subject = new Subject<Object>();
    // Observable string streams
    change$ = this.subject.asObservable();

    // Service message commands
    change(data: Object) {
        this.subject.next(data);
    }
}

export interface PageData {
    hasChildToolbar?: boolean;
}