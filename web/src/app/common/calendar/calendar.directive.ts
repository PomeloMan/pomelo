import { Directive, ElementRef, HostBinding, HostListener, Input } from '@angular/core';

// ngOnChanges - 当数据绑定输入属性的值发生变化时调用
// ngOnInit - 在第一次 ngOnChanges 后调用
// ngDoCheck - 自定义的方法，用于检测和处理值的改变
// ngAfterContentInit - 在组件内容初始化之后调用
// ngAfterContentChecked - 组件每次检查内容时调用
// ngAfterViewInit - 组件相应的视图初始化之后调用
// ngAfterViewChecked - 组件每次检查视图时调用
// ngOnDestroy - 指令销毁前调用
@Directive({ selector: '[calendar-title]' })
export class CalendarTitle {

    constructor(private el: ElementRef) { }

    @HostBinding('style.float') float = 'left';
    @HostBinding('style.text-align') align = 'center';

    // @HostBinding('style.width') width = 'calc(100%/7)';
    ngOnInit(): void {
        this.el.nativeElement.style.width = 'calc(100%/7)';
    }
}