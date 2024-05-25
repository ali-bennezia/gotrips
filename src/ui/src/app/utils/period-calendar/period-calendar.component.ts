import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-period-calendar',
  templateUrl: './period-calendar.component.html',
  styleUrls: ['./period-calendar.component.css'],
})
export class PeriodCalendarComponent {
  @Input()
  label!: string;

  @Input()
  dayDisabledPredicate?: (date: Date) => boolean;

  @Output()
  onSelectedFirstDate: EventEmitter<Date> = new EventEmitter<Date>();
  @Output()
  onSelectedSecondDate: EventEmitter<Date> = new EventEmitter<Date>();
  @Output()
  onChangedViewedMonth: EventEmitter<Date> = new EventEmitter<Date>();
  @Output()
  onChangedFirstKnownDate: EventEmitter<Date> = new EventEmitter<Date>();
  @Output()
  onChangedLastKnownDate: EventEmitter<Date> = new EventEmitter<Date>();

  displayedDays: number[] = [];
  displayedFirstViewedMonthDayIndex: number = 0;
  displayedLastViewedMonthDayIndex: number = 0;
  viewedMonthFirstWeekDay: number = 0;
  firstKnownDate: Date | null = null;
  lastKnownDate: Date | null = null;

  selectedFirstDate: Date | null = null;
  selectedFirstCalendarIndex: number | null = null;

  selectedSecondDate: Date | null = null;
  selectedSecondCalendarIndex: number | null = null;

  selectionMode: boolean = false;

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
      !el.classList.contains('disabled') &&
      this.selectionMode == false
    ) {
    } else {
      e.stopPropagation();
    }
  }

  hoveredDateCalendarIndex: number | null = null;
  hoveredPeriodContiguous: boolean = true;
  onMouseEnterCalendarDate(e: Event, calendarIndex: number) {
    if (this.selectionMode == false) return;
    this.hoveredDateCalendarIndex = calendarIndex;
    this.hoveredPeriodContiguous = this.isHoveredPeriodContiguous();
  }

  onMouseEnterLeaveCalendarDate(e: Event, calendarIndex: number) {
    if (this.selectionMode == false) return;
    this.hoveredDateCalendarIndex = null;
    this.hoveredPeriodContiguous = true;
  }

  isSameDay(i: number) {
    return i === this.selectedFirstCalendarIndex;
  }

  isDayWithinSelectedPeriod(i: number) {
    if (
      this.selectedFirstCalendarIndex == null ||
      this.selectedSecondCalendarIndex == null
    )
      return false;
    return (
      i >= this.selectedFirstCalendarIndex &&
      i <= this.selectedSecondCalendarIndex
    );
  }

  isDayWithinHoveredPeriod(i: number) {
    if (
      this.selectedFirstCalendarIndex == null ||
      this.hoveredDateCalendarIndex == null
    )
      return false;
    return (
      i >= this.selectedFirstCalendarIndex && i <= this.hoveredDateCalendarIndex
    );
  }

  isHoveredPeriodContiguous() {
    if (
      this.selectedFirstCalendarIndex == null ||
      this.hoveredDateCalendarIndex == null
    )
      return true;
    for (
      let i = this.selectedFirstCalendarIndex;
      i <= this.hoveredDateCalendarIndex;
      ++i
    ) {
      if (this.isDayDisabled(i)) return false;
    }
    return true;
  }

  isDayDisabled(calendarIndex: number) {
    if (this.dayDisabledPredicate) {
      return this.dayDisabledPredicate(this.getDayDate(calendarIndex));
    } else return false;
  }

  getDayDateFromDisplayIndex(displayIndex: number) {
    let newDate = new Date(this.firstKnownDate!);
    newDate.setDate(newDate.getDate() + displayIndex);
    return newDate;
  }

  getDayDate(calendarIndex: number) {
    let dIndex = this.getDisplayedDaysIndexFromCalendarIndex(calendarIndex);
    return this.getDayDateFromDisplayIndex(dIndex);
  }

  selectFirstDate(d: Date | null, fireEvent: boolean) {
    this.selectedFirstDate = d;
    this.selectedFirstCalendarIndex = null;
    if (fireEvent && d != null) this.onSelectedFirstDate.next(d);
  }

  selectSecondDate(d: Date | null, fireEvent: boolean) {
    this.selectedSecondDate = d;
    this.selectedSecondCalendarIndex = null;
    if (fireEvent && d != null) this.onSelectedSecondDate.next(d);
  }

  selectFirstCalendarDate(calendarIndex: number, fireEvent: boolean) {
    this.selectedFirstDate = this.getDayDate(calendarIndex);
    this.selectedFirstCalendarIndex = calendarIndex;
    if (fireEvent) this.onSelectedFirstDate.next(this.selectedFirstDate);
  }

  selectSecondCalendarDate(calendarIndex: number, fireEvent: boolean) {
    this.selectedSecondDate = this.getDayDate(calendarIndex);
    this.selectedSecondCalendarIndex = calendarIndex;
    if (fireEvent) this.onSelectedSecondDate.next(this.selectedSecondDate);
  }

  onClickDay(calendarIndex: number) {
    if (this.isDayDisabled(calendarIndex) || !this.hoveredPeriodContiguous)
      return;
    if (this.selectionMode == false) {
      this.selectFirstCalendarDate(calendarIndex, true);
      this.selectSecondDate(null, false);

      this.selectionMode = true;
    } else {
      this.selectSecondCalendarDate(calendarIndex, true);

      this.selectionMode = false;
    }
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
    this.lastKnownDate = this.getDayDateFromDisplayIndex(
      this.displayedDays.length - 1
    );
    this.onChangedFirstKnownDate.next(this.firstKnownDate);
    this.onChangedLastKnownDate.next(this.lastKnownDate);
    this.onChangedViewedMonth.next(this.viewedMonth);
  }
  get viewedMonth() {
    return this._viewedMonth;
  }

  ngOnInit(): void {
    this.viewedMonth = new Date();
  }
}
