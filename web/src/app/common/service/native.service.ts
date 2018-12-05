import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material';

@Injectable()
export class NativeService {

    constructor(
        public snackBar: MatSnackBar
    ) { }

    openSnackBar(message: string, action: string) {
        this.snackBar.open(message, action, {
            duration: 2000,
            horizontalPosition: 'right',
            verticalPosition: 'top'
        });
    }
}