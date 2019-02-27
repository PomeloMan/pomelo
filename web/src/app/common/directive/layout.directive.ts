import { Directive, ElementRef, Input, TemplateRef } from '@angular/core';

@Directive({
    selector: '[layout]'
})
export class LayoutDirective {

    @Input() layoutId: string;

    constructor(public template: TemplateRef<ElementRef>) { }
}