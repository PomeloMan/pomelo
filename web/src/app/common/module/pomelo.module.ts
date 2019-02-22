import { NgModule } from '@angular/core';
import { NgPopoverModule } from '../component/ng-popover/ng-popover.module';

@NgModule({
    imports: [
        NgPopoverModule
    ],
    exports: [
        NgPopoverModule
    ]
})
export class PomeloModule { }