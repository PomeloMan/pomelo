import { HttpHeaders } from "@angular/common/http";

export const API = {
    AUTH_URL: '',
    LOGIN_URL: 'login',
    USER_LIST_URL: 'user/list',
    USER_PAGE_URL: 'user/page',
    USER_INFO_URL: 'user/info',
    LANGUAGE_LIST_URL: 'language/list'
}

/**
 * options: { observe: 'response' }
 */
export interface ResponseEntity<T> {
    body?: T;
    headers?: HttpHeaders;
    ok?: boolean;
    status?: number;
    statusText?: string;
    type?: number;
    url?: string;
}

export interface Page<T> {
    content?: T;
    first?: boolean;
    last?: boolean;
    number?: number
    numberOfElements?: number
    size?: number
    sort?: string
    totalElements?: number
    totalPages?: number
}
