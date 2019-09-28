import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IResolution } from 'app/shared/model/resolution.model';
import { AccountService } from 'app/core';
import { Council } from './council.model';
import { IPerson } from './person.model';
import { PersonService } from 'app/entities/person';

@Component({
  selector: 'jhi-person-embedded',
  templateUrl: './person-embedded.component.html'
})
export class PersonEmbeddedComponent implements OnInit, OnDestroy {
  persons: IPerson[];
  currentAccount: any;
  eventSubscriber: Subscription;
  @Input() council: Council;

  constructor(
    protected personService: PersonService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.personService
      .query({ councilId: this.council.id, council: true, duty: true, department: true })
      .pipe(
        filter((res: HttpResponse<IPerson[]>) => res.ok),
        map((res: HttpResponse<IPerson[]>) => res.body)
      )
      .subscribe(
        (res: IPerson[]) => {
          this.persons = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInResolutions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IResolution) {
    return item.id;
  }

  registerChangeInResolutions() {
    this.eventSubscriber = this.eventManager.subscribe('resolutionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
