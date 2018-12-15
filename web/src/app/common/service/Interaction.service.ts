import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

/**
 * Data interaction between components
 * {@link https://www.angular.cn/guide/component-interaction#parent-and-children-communicate-via-a-service}
 */
@Injectable()
export class InteractionService {

    // Observable string sources
    private page = new Subject<string>()

    // Observable string streams
    pageChange$ = this.page.asObservable();

    // Service message commands
    pageChange(option: string) {
        this.page.next(option);
    }
}
