import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthorityRoutingModule } from './authority-routing.module';
import { AuthorityComponent } from './authority.component';
import { AuthService } from '../../../../auth/auth.service';
import { MatSortModule, MatPaginatorModule, MatTableModule, MatProgressSpinnerModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,

    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,

    AuthorityRoutingModule
  ],
  declarations: [
    AuthorityComponent
  ],
  providers: [
    AuthService
  ]
})
export class AuthorityModule { }
