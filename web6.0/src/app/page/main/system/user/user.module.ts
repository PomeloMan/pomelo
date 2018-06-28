import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MatTableModule,
  MatPaginatorModule,
  MatSortModule
} from '@angular/material';

import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';

@NgModule({
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    UserRoutingModule
  ],
  declarations: [UserComponent]
})
export class UserModule { }
