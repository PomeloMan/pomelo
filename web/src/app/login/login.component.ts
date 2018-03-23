import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { User, UserService } from '../main/user/user.service';
import { StorageService } from '../service/storage.service';
import { TOKEN } from '../service/auth.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css'],
    providers: [UserService]
})

export class LoginComponent implements OnInit {

    user: User;

    constructor(
        private userService: UserService,
        private storageService: StorageService,
        private router: Router
    ) { }

    ngOnInit(): void {
        this.user = new User();
    }

    login() {
        this.userService.login(this.user).subscribe(
            resp => {
                this.storageService.setData(TOKEN, resp.headers.get('Authorization'));
                this.router.navigate(['/main']);
            },
            error => {
                console.log(error);
            }
        );
    }
    test() {
        this.userService.me();
    }
}