import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { User, UserService } from '../user/user.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

    user: User;

    constructor(private userService: UserService, private router: Router) { }

    ngOnInit(): void {
        if (!this.user) {
            this.user = new User();
        }
    }

    login() {
        this.userService.login(this.user);
    }
    test() {
        this.userService.me();
    }
}