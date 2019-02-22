export interface IPage {
    page: number;
    size: number;
    order: string;
    dir: string;
    object: any;
}

export interface Page {
    content: any;
    totalPages: number;
    totalElements: number;
    last: boolean;
    first: boolean;
    number: number;
    size: number;
    sort: any;
    numberOfElements: number
}
