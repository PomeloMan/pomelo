import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { MatProgressBarModule, MatSnackBarModule } from '@angular/material';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page/not-found/not-found.component';

import { StorageService } from './common/service/storage.service';
import { ApiService } from './config/api.service';
import { AuthGuard } from './config/security/auth-guard.service';
import { AuthService } from './config/security/auth.service';
import { HTTP_INTERCEPTOR_PROVIDERS } from './http-interceptors/_index';
import { InteractionService } from './common/service/Interaction.service';

import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { far } from '@fortawesome/free-regular-svg-icons';// npm install @fortawesome/free-regular-svg-icons --save
import { fab } from '@fortawesome/free-brands-svg-icons';// npm install @fortawesome/free-brands-svg-icons --save
import { NativeService } from './common/service/native.service';

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,//animation
    HttpClientModule,//http
    MatProgressBarModule,
    AppRoutingModule,
    MatSnackBarModule
  ],
  providers: [
    StorageService,
    ApiService,
    AuthService,
    AuthGuard,
    InteractionService,
    NativeService,
    HTTP_INTERCEPTOR_PROVIDERS//http拦截器
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor(
    router: Router
  ) {
    console.log('Routes: ', JSON.stringify(router.config, undefined, 2));
    library.add(fas, far, fab);
  }
}
