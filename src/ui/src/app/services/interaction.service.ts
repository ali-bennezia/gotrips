import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InteractionService {
  onConfirmAccountDeleteSource: Subject<Event> = new Subject();
  onConfirmAccountDelete$: Observable<Event> =
    this.onConfirmAccountDeleteSource.asObservable();

  constructor() {}
}
