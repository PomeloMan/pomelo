import {
    trigger,
    state,
    style,
    animate,
    transition,
    keyframes,
    group
} from '@angular/animations';

export const EXPAND_TO_LEFT = trigger('expandToLeft', [
    state('in', style({ transform: 'scale(0, 0)' })),
    transition('void => *', [
        animate('0.25s 0s ease-in', keyframes([
            style({ transform: 'scale(0, 0)', transformOrigin: 'right bottom' }),
            style({ transform: 'scale(1, 1)', transformOrigin: 'right bottom' }),
        ]))
    ]),
    transition('* => void', [
        animate('0.25s 0s ease-out', keyframes([
            style({ transform: 'scale(1, 1)', transformOrigin: 'right bottom' }),
            style({ transform: 'scale(0, 0)', transformOrigin: 'right bottom' }),
        ]))
    ])
])

export const FADE_IN_UP = trigger('fadeInUp', [
    state('in', style({ transform: 'translateY(0px)' })),
    transition('void => *', [
        animate('1s 0s ease', keyframes([
            style({ opacity: 0, transform: 'translateY(200px)', offset: 0 }),
            style({ opacity: 1, transform: 'translateY(0px)', offset: 1.0 })
        ]))
    ])
])

export const FADE_IN_DOWN = trigger('fadeInDown', [
    state('in', style(
        {
            opacity: 1,
            transform: 'translateY(0)'
        }
    )),
    transition('void => *', [
        style({
            opacity: 0,
            transform: 'translateY(-200px)'
        }),
        animate(200)
    ])
])

export const toggle = trigger('toggle', [
    state('inactive', style({
        height: '120px'
    })),
    state('active', style({
        height: '96px',
    })),
    transition('inactive <=> active', animate('5s ease-out'))
])

export const FLY_IN_OUT = trigger('flyInOut', [
    state('in', style({ opacity: 1, transform: 'translateX(0) scale(1)' })),
    // 进场动画
    transition('void => *', [
        style({
            opacity: 0,
            transform: 'translateX(-100%) scale(0)'
        }),
        animate('0.5s ease-in')
    ]),
    // 出场动画
    transition('* => void', [
        animate('0.5s 0s ease-out', style({
            opacity: 0,
            transform: 'translateX(100%) scale(0)'
        }))
    ])
])

export const FLY_IN_OUT_KEYFRAMES = trigger('flyInOut', [
    state('in', style({ transform: 'translateX(0)' })),
    transition('void => *', [
        animate(1000, keyframes([
            style({ opacity: 0, transform: 'translateX(-100%)', offset: 0 }),
            style({ opacity: 1, transform: 'translateX(15px)', offset: 0.3 }),
            style({ opacity: 1, transform: 'translateX(0)', offset: 1.0 })
        ]))
    ]),
    transition('* => void', [
        animate(1000, keyframes([
            style({ opacity: 1, transform: 'translateX(0)', offset: 0 }),
            style({ opacity: 1, transform: 'translateX(-15px)', offset: 0.7 }),
            style({ opacity: 0, transform: 'translateX(100%)', offset: 1.0 })
        ]))
    ])
])

export const FLY_IN_OUT_GROUP = trigger('flyInOut', [
    state('in', style({ width: 120, transform: 'translateX(0)', opacity: 1 })),
    transition('void => *', [
        style({ width: 10, transform: 'translateX(50px)', opacity: 0 }),
        group([
            animate('0.3s 0.1s ease', style({
                transform: 'translateX(0)',
                width: 120
            })),
            animate('0.3s ease', style({
                opacity: 1
            }))
        ])
    ]),
    transition('* => void', [
        group([
            animate('0.3s ease', style({
                transform: 'translateX(50px)',
                width: 10
            })),
            animate('0.3s 0.2s ease', style({
                opacity: 0
            }))
        ])
    ])
])