import { Component, OnInit, ViewChild, TemplateRef, ViewContainerRef, HostBinding, Input } from '@angular/core';
import { MatButton } from '@angular/material';
import { OverlayRef, Overlay } from '@angular/cdk/overlay';
import { TemplatePortal } from '@angular/cdk/portal';
import { EXPAND_TO_LEFT } from '../../animations';

@Component({
  selector: 'ng-popover',
  templateUrl: './ng-popover.component.html',
  styleUrls: ['./ng-popover.component.scss'],
  animations: [
    EXPAND_TO_LEFT
  ]
})
export class NgPopoverComponent implements OnInit {

  @HostBinding('class') class = 'ng-popover';
  @ViewChild('PopoverLayout') popoverLayout: TemplateRef<any>;
  @ViewChild('PopoverButton') popoverButton: MatButton;
  overlayRef: OverlayRef;

  @Input() position: string;// left-top/left-center/left-bottom
  // center-top/center-center/center-bottom
  // right-top/right-center/right-bottom

  _position = {
    leftTop: {
      originPos: { originX: 'start', originY: 'top' },
      overlayPos: { overlayX: 'end', overlayY: 'top' }
    },
    left: {
      originPos: { originX: 'start', originY: 'center' },
      overlayPos: { overlayX: 'end', overlayY: 'center' }
    },
    leftBottom: {
      originPos: { originX: 'start', originY: 'bottom' },
      overlayPos: { overlayX: 'end', overlayY: 'bottom' }
    },
    rightTop: {
      originPos: { originX: 'end', originY: 'top' },
      overlayPos: { overlayX: 'start', overlayY: 'top' }
    },
    right: {
      originPos: { originX: 'end', originY: 'center' },
      overlayPos: { overlayX: 'start', overlayY: 'center' }
    },
    rightBottom: {
      originPos: { originX: 'end', originY: 'bottom' },
      overlayPos: { overlayX: 'start', overlayY: 'bottom' }
    },
    topLeft: {
      originPos: { originX: 'start', originY: 'top' },
      overlayPos: { overlayX: 'start', overlayY: 'bottom' }
    },
    top: {
      originPos: { originX: 'center', originY: 'top' },
      overlayPos: { overlayX: 'center', overlayY: 'bottom' }
    },
    topRight: {
      originPos: { originX: 'end', originY: 'top' },
      overlayPos: { overlayX: 'end', overlayY: 'bottom' }
    },
    bottomLeft: {
      originPos: { originX: 'start', originY: 'bottom' },
      overlayPos: { overlayX: 'start', overlayY: 'top' }
    },
    bottom: {
      originPos: { originX: 'center', originY: 'bottom' },
      overlayPos: { overlayX: 'center', overlayY: 'top' }
    },
    bottomRight: {
      originPos: { originX: 'end', originY: 'bottom' },
      overlayPos: { overlayX: 'end', overlayY: 'top' }
    }
  }

  constructor(
    private overlay: Overlay,
    private viewContainerRef: ViewContainerRef
  ) { }

  ngOnInit() {
    const strategy = this.overlay
      .position()
      .connectedTo(this.popoverButton._elementRef, this.getOriginConnectionPosition(), this.getOverlayConnectionPosition());

    this.overlayRef = this.overlay.create({
      hasBackdrop: true,
      backdropClass: 'cdk-overlay-transparent-backdrop',
      positionStrategy: strategy,
      scrollStrategy: this.overlay.scrollStrategies.reposition()
    });

    this.overlayRef.backdropClick().subscribe(() => {
      this.overlayRef.detach();
    });
  }

  toggle() {
    if (this.overlayRef && this.overlayRef.hasAttached()) {
      this.overlayRef.detach();
    } else {
      this.overlayRef.attach(new TemplatePortal(this.popoverLayout, this.viewContainerRef));
    }
  }

  /**
   * Reference {@link src/reference/cdk-overlay-connect-position.png}
   */
  getOriginConnectionPosition() {
    return this._position[this.position].originPos;
  }

  /**
   * Reference {@link src/reference/cdk-overlay-connect-position.png}
   */
  getOverlayConnectionPosition() {
    return this._position[this.position].overlayPos;
  }
}
