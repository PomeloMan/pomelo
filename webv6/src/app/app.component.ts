import { Component, OnInit } from '@angular/core';
import { Router, NavigationStart, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  navigating: boolean = true;

  constructor(
    private router: Router
  ) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.navigating = true;
      }
      if (event instanceof NavigationEnd) {
        this.navigating = false;
      }
    }, error => {
      console.error(error);
      this.navigating = false;
    });
  }
}