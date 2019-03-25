import { Component, Input, ElementRef, HostListener, Inject, Optional } from '@angular/core';
import { MatButton } from '@angular/material';
import { Platform } from '@angular/cdk/platform';
import { FocusMonitor } from '@angular/cdk/a11y';
import { ANIMATION_MODULE_TYPE } from '@angular/platform-browser/animations';

@Component({
  selector: 'matx-loading-button',
  templateUrl: './mat-ext-button.component.html',
  styleUrls: ['./mat-ext-button.component.scss'],
  host : {
    'class' : 'mat-raised-button matx-loading-button'
   }
})
export class MatExtButtonComponent extends MatButton {

  @Input() loading: boolean = false;

  @HostListener('click') onClick() {
    if (!this.loading) {
      this.loading = true;
      this.disabled = true;
      this._elementRef.nativeElement.setAttribute('disabled', true);
    }
  }

  constructor(
    platform: Platform,
    focusMonitor: FocusMonitor,
    elementRef: ElementRef,
    @Optional() @Inject(ANIMATION_MODULE_TYPE) animationMode?: string
  ) {
    super(elementRef, platform, focusMonitor, animationMode);
  }
}
