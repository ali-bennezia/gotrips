import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css'],
})
export class CalendarComponent implements OnInit {
  @Input()
  label!: string;

  @Input()
  dayDisabledPredicate?: (date: Date) => boolean;

  @Output()
  onSelectedDate: EventEmitter<Date> = new EventEmitter<Date>();

  displayedDays: number[] = [];
  displayedFirstViewedMonthDayIndex: number = 0;
  displayedLastViewedMonthDayIndex: number = 0;
  viewedMonthFirstWeekDay: number = 0;
  firstKnownDate: Date | null = null;
  selectedDate: Date | null = null;
  selectedCalendarIndex: number | null = null;

  getDisplayedDaysIndexFromCalendarIndex(i: number) {
    return (
      this.displayedFirstViewedMonthDayIndex +
      i -
      (this.viewedMonthFirstWeekDay - 1) -
      2
    );
  }

  isDayInViewedMonth(i: number) {
    let z = this.getDisplayedDaysIndexFromCalendarIndex(i);
    return (
      z >= this.displayedFirstViewedMonthDayIndex &&
      z <= this.displayedLastViewedMonthDayIndex
    );
  }

  onClickViewNextMonth(e: Event) {
    e.preventDefault();
    this.viewedMonth = new Date(
      this.viewedMonth.getFullYear(),
      this.viewedMonth.getMonth() + 1,
      1
    );
  }

  onClickViewPreviousMonth(e: Event) {
    e.preventDefault();
    this.viewedMonth = new Date(
      this.viewedMonth.getFullYear(),
      this.viewedMonth.getMonth() - 1,
      1
    );
  }

  onClickInside(e: Event) {
    let pt = e as PointerEvent;
    let el = pt.target as HTMLElement;
    if (
      el.tagName.toLocaleLowerCase() == 'td' &&
      !el.classList.contains('disabled')
    ) {
    } else {
      e.stopPropagation();
    }
  }

  isSameDay(i: number) {
    return i === this.selectedCalendarIndex;
  }

  isDayDisabled(calendarIndex: number) {
    if (this.dayDisabledPredicate) {
      return this.dayDisabledPredicate(this.getDayDate(calendarIndex));
    } else return false;
  }

  getDayDate(calendarIndex: number) {
    let dIndex = this.getDisplayedDaysIndexFromCalendarIndex(calendarIndex);
    let newDate = new Date(this.firstKnownDate!);
    newDate.setDate(newDate.getDate() + dIndex);
    return newDate;
  }

  onClickDay(calendarIndex: number) {
    if (this.isDayDisabled(calendarIndex)) return;
    this.selectedDate = this.getDayDate(calendarIndex);
    this.selectedCalendarIndex = calendarIndex;
    this.onSelectedDate.next(this.selectedDate);
  }

  private _viewedMonth: Date = new Date();
  set viewedMonth(val: Date) {
    this._viewedMonth = val;

    let previousMonthDaysCount = new Date(
      val.getFullYear(),
      val.getMonth(),
      0
    ).getDate();

    let viewedMonthDaysCount = new Date(
      val.getFullYear(),
      val.getMonth() + 1,
      0
    ).getDate();

    let nextMonthDaysCount = new Date(
      val.getFullYear(),
      val.getMonth() + 2,
      0
    ).getDate();

    this.viewedMonthFirstWeekDay = new Date(
      val.getFullYear(),
      val.getMonth(),
      1
    ).getDay();

    this.displayedDays = [];
    for (let i = 1; i <= previousMonthDaysCount; ++i) {
      this.displayedDays.push(i);
    }
    this.displayedFirstViewedMonthDayIndex = previousMonthDaysCount;
    this.displayedLastViewedMonthDayIndex =
      previousMonthDaysCount + viewedMonthDaysCount - 1;

    for (let i = 1; i <= viewedMonthDaysCount; ++i) {
      this.displayedDays.push(i);
    }

    for (let i = 1; i <= nextMonthDaysCount; ++i) {
      this.displayedDays.push(i);
    }

    this.firstKnownDate = new Date(val.getFullYear(), val.getMonth() - 1, 1);
  }
  get viewedMonth() {
    return this._viewedMonth;
  }

  ngOnInit(): void {
    this.viewedMonth = new Date();
  }
}
