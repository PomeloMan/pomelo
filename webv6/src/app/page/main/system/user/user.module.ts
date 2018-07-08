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
  MatToolbarModule,
  MatDividerModule,
  MatSelectModule,
  MatSlideToggleModule,
  MatTooltipModule
} from '@angular/material';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserRoutingModule } from './user-routing.module';

import { UserComponent } from './user.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { UsernameValidatorDirective } from '../../../../common/validator/username-validator.directive';
import { WindowService } from '../../../../config/window.sevice';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,//FormControl

    // layout
    MatGridListModule,
    MatDividerModule,
    MatTabsModule,
    MatCardModule,
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
    MatSelectModule,
    MatSlideToggleModule,
    // navigation
    MatToolbarModule,
    // popups & modals
    MatTooltipModule,

    UserRoutingModule
  ],
  declarations: [
    UserComponent,
    UserDetailComponent,
    UsernameValidatorDirective,
  ],
  providers: [
    WindowService
  ]
})
export class UserModule { }
