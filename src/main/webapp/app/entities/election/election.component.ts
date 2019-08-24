import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IElection } from 'app/shared/model/election.model';
import { AccountService } from 'app/core';
import { ElectionService } from './election.service';

@Component({
  selector: 'jhi-election',
  templateUrl: './election.component.html'
})
export class ElectionComponent implements OnInit, OnDestroy {
  elections: IElection[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected electionService: ElectionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.electionService
      .query()
      .pipe(
        filter((res: HttpResponse<IElection[]>) => res.ok),
        map((res: HttpResponse<IElection[]>) => res.body)
      )
      .subscribe(
        (res: IElection[]) => {
          this.elections = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInElections();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IElection) {
    return item.id;
  }

  registerChangeInElections() {
    this.eventSubscriber = this.eventManager.subscribe('electionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
