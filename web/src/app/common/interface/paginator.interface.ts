import { PageEvent } from "@angular/material";

export interface Paginator {

    // MatPaginator Inputs
    length: number;
    pageIndex: number;
    pageSize: number;
    pageSizeOptions: number[];

    page(pageEvent: PageEvent)
}