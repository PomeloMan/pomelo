import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';

import { MainModule } from './main/main.module';

import { AppComponent } from './app.component';

import { StorageService } from './service/storage.service';

// {@link https://www.angular.cn/guide/ngmodule}
// declarations - 声明本模块中拥有的视图类。Angular 有三种视图类：组件、指令和管道。
// exports - declarations 的子集，可用于其它模块的组件模板。
// imports - 本模块声明的组件模板需要的类所在的其它模块。
// providers - 服务的创建者，并加入到全局服务列表中，可用于应用任何部分。
// bootstrap - 指定应用的主视图（称为根组件），它是所有其它视图的宿主。只有根模块才能设置bootstrap属性。
@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpModule,
    HttpClientModule,
    AppRoutingModule,
    MainModule
  ],
  declarations: [
    AppComponent
  ],
  exports: [],
  providers: [StorageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
