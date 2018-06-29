import { NgModule } from '@angular/core';
import {
  MatTableModule,
  MatPaginatorModule,
  MatSortModule,
  MatInputModule,
  MatFormFieldModule,
  MatIconModule,
  MatButtonModule
} from '@angular/material';

import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UsernameValidatorDirective } from '../../../../common/validator/username-validator.directive';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    // ReactiveFormsModule,

    MatButtonModule,
    MatIconModule,

    MatTableModule,
    MatPaginatorModule,
    MatSortModule,

    MatFormFieldModule,
    MatInputModule,

    UserRoutingModule
  ],
  declarations: [
    UserComponent,
    UsernameValidatorDirective
  ]
})
export class UserModule { }
