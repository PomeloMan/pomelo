import { NgModule } from '@angular/core';
import {
  MatTableModule,
  MatPaginatorModule,
  MatSortModule,
  MatInputModule,
  MatFormFieldModule,
  MatIconModule,
  MatButtonModule,
  MatGridListModule,
  MatTabsModule,
  MatCardModule,
  MatToolbarModule
} from '@angular/material';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserRoutingModule } from './user-routing.module';

import { UserComponent } from './user.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { UsernameValidatorDirective } from '../../../../common/validator/username-validator.directive';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    // ReactiveFormsModule,

    // layout
    MatGridListModule,
    // button
    MatButtonModule,
    MatIconModule,
    // table
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    // form
    MatFormFieldModule,
    MatInputModule,
    // other
    MatTabsModule,
    MatCardModule,
    MatToolbarModule,

    UserRoutingModule
  ],
  declarations: [
    UserComponent,
    UserDetailComponent,
    UsernameValidatorDirective,
  ]
})
export class UserModule { }
