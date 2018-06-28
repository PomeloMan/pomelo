import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page/not-found/not-found.component';

import { StorageService } from './config/storage.service';
import { ApiService } from './config/api.service';
import { AuthGuard } from './auth/auth-guard.service';
import { AuthService } from './auth/auth.service';
import { HTTP_INTERCEPTOR_PROVIDERS } from './http-interceptors';

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,//animation
    HttpClientModule,//http
    AppRoutingModule
  ],
  providers: [
    StorageService,
    ApiService,
    AuthService,
    AuthGuard,
    HTTP_INTERCEPTOR_PROVIDERS//http拦截器
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(router: Router) {
    console.log('Routes: ', JSON.stringify(router.config, undefined, 2));
  }
}
