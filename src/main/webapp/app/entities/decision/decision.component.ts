import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDecision } from 'app/shared/model/decision.model';
import { AccountService } from 'app/core';
import { DecisionService } from './decision.service';

@Component({
  selector: 'jhi-decision',
  templateUrl: './decision.component.html'
})
export class DecisionComponent implements OnInit, OnDestroy {
  decisions: IDecision[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected decisionService: DecisionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.decisionService
      .query()
      .pipe(
        filter((res: HttpResponse<IDecision[]>) => res.ok),
        map((res: HttpResponse<IDecision[]>) => res.body)
      )
      .subscribe(
        (res: IDecision[]) => {
          this.decisions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDecisions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDecision) {
    return item.id;
  }

  registerChangeInDecisions() {
    this.eventSubscriber = this.eventManager.subscribe('decisionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
