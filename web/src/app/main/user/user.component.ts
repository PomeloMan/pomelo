import { Component, OnInit } from '@angular/core';

import { AuthService } from '../../service/auth.service';
import { UserService } from './user.service';

@Component({
    selector: 'app-user',
    templateUrl: './user.component.html',
    styleUrls: [],
    providers: [UserService]
})

export class UserComponent implements OnInit {

    constructor(
        private authService: AuthService,
        private userService: UserService
    ) { }

    ngOnInit(): void {
        this.authService.clear();
    }

}