import { Component } from '@angular/core';
import { FLY_IN_OUT, toggle } from '../../common/animations';
import { CalendarView, CalendarEvent, CalendarEventTimesChangedEvent, CalendarEventAction } from 'angular-calendar';
import { Subject } from 'rxjs';
import {
	startOfDay,
	subDays,
	addDays,
	endOfMonth,
	addHours,
	isSameMonth,
	isSameDay
} from 'date-fns';

@Component({
	selector: 'app-dashboard',
	templateUrl: './dashboard.component.html',
	styleUrls: ['./dashboard.component.css'],
	animations: [
		toggle,
		FLY_IN_OUT,
	]
})

export class DashboardComponent {

	tiles = [
		"Simplecard1",
		"Simplecard2",
		"Simplecard3",
		"Simplecard4"
	]

	active: boolean = true;
	state: string = this.active ? 'active' : 'inactive';

	constructor(
	) {
	}

	toggle() {
		this.active = !this.active;
		this.state = this.active ? 'active' : 'inactive';
	}

	// Calendar
	actions: CalendarEventAction[] = [
		{
			label: '<i class="fa fa-fw fa-pencil"></i>',
			onClick: ({ event }: { event: CalendarEvent }): void => {
				this.handleEvent('Edited', event);
			}
		},
		{
			label: '<i class="fa fa-fw fa-times"></i>',
			onClick: ({ event }: { event: CalendarEvent }): void => {
				this.events = this.events.filter(iEvent => iEvent !== event);
				this.handleEvent('Deleted', event);
			}
		}
	];
	CalendarView = CalendarView;
	view: CalendarView = CalendarView.Month;
	viewDate: Date = new Date();
	events: CalendarEvent[] = [
		{
			start: subDays(startOfDay(new Date()), 1),
			end: addDays(new Date(), 1),
			title: 'A 3 day event',
			color: colors.red,
			actions: this.actions,
			allDay: true,
			resizable: {
				beforeStart: true,
				afterEnd: true
			},
			draggable: true
		},
		{
			start: startOfDay(new Date()),
			title: 'An event with no end date',
			color: colors.yellow,
			actions: this.actions
		},
		{
			start: subDays(endOfMonth(new Date()), 3),
			end: addDays(endOfMonth(new Date()), 3),
			title: 'A long event that spans 2 months',
			color: colors.blue,
			allDay: true
		},
		{
			start: addHours(startOfDay(new Date()), 2),
			end: new Date(),
			title: 'A draggable and resizable event',
			color: colors.yellow,
			actions: this.actions,
			resizable: {
				beforeStart: true,
				afterEnd: true
			},
			draggable: true
		}
	];
	refresh: Subject<any> = new Subject();

	modalData: {
		action: string;
		event: CalendarEvent;
	};

	handleEvent(action: string, event: CalendarEvent): void {
		this.modalData = { event, action };
	}

	activeDayIsOpen: boolean = true;
	dayClicked({ date, events }: { date: Date; events: CalendarEvent[]; }): void {
		if (isSameMonth(date, this.viewDate)) {
			if (
				(isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
				events.length === 0
			) {
				this.activeDayIsOpen = false;
			} else {
				this.activeDayIsOpen = true;
			}
			this.viewDate = date;
		}
	}

	eventTimesChanged({
		event,
		newStart,
		newEnd
	}: CalendarEventTimesChangedEvent): void {
		event.start = newStart;
		event.end = newEnd;
		this.handleEvent('Dropped or resized', event);
		this.refresh.next();
	}
}

const colors: any = {
	red: {
		primary: '#ad2121',
		secondary: '#FAE3E3'
	},
	blue: {
		primary: '#1e90ff',
		secondary: '#D1E8FF'
	},
	yellow: {
		primary: '#e3bc08',
		secondary: '#FDF1BA'
	}
};