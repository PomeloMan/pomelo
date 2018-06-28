import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthorityRoutingModule } from './authority-routing.module';
import { AuthorityComponent } from './authority.component';

@NgModule({
  imports: [
    CommonModule,
    AuthorityRoutingModule
  ],
  declarations: [AuthorityComponent]
})
export class AuthorityModule { }
