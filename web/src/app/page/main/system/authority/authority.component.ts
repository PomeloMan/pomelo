import { Component, OnInit, ViewChild } from '@angular/core';
import { merge, of } from 'rxjs';
import { Authority, AuthorityService } from './authority.service';
import { startWith, switchMap, map, catchError, finalize } from 'rxjs/operators';
import { MatSort, MatPaginator } from '@angular/material';

@Component({
  selector: 'app-authority',
  templateUrl: './authority.component.html',
  styleUrls: ['./authority.component.scss']
})
export class AuthorityComponent implements OnInit {

  displayedColumns: string[] = ['number', 'name', 'createdDate'];
  // dataSource: Observable<Authority[]>;
  dataSource: Authority[];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  isloading: boolean = false;
  totalLength = 0;

  constructor(
    public service: AuthorityService
  ) { }

  ngOnInit() {
    this.isloading = true;
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isloading = true;
          return this.service.getAuthorities(
            this.sort.active, this.sort.direction, this.paginator.pageIndex);
        }),
        map(res => {
          this.totalLength = res.length;
          return res;
        }),
        finalize(() => this.isloading = false),
        catchError(() => {
          this.isloading = false;
          this.totalLength = 0;
          return of([]);
        })
      )
      .subscribe(data => this.dataSource = data);
    // this.sort.sortChange.subscribe(res => {
    //   this.isloading = true;
    //   this.getData();
    // })
    // this.paginator.page.subscribe(res => {
    //   this.isloading = true;
    //   this.getData();
    // })
    // this.getData()
  }

  // getData() {
  //   this.service.getAuthorities(this.sort.active, this.sort.direction, this.paginator.pageIndex)
  //     .pipe(
  //       finalize(() => this.isloading = false)
  //     )
  //     .subscribe(res => {
  //       this.dataSource = res;
  //       this.totalLength = res.length;
  //     });
  // }
}
