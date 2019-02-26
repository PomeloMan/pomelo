import { NgModule } from "@angular/core";
import { ChTimelineModule } from "./ch-timeline/ch-timeline.module";
import { ChButtonGroupModule } from "./ch-button-group/ch-button-group.module";

@NgModule({
    imports: [
        ChTimelineModule,
        ChButtonGroupModule
    ],
    exports: [
        ChTimelineModule,
        ChButtonGroupModule
    ]
})
export class ChModule { }