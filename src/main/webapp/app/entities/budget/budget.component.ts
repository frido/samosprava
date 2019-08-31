import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBudget } from 'app/shared/model/budget.model';
import { AccountService } from 'app/core';
import { BudgetService } from './budget.service';

@Component({
  selector: 'jhi-budget',
  templateUrl: './budget.component.html'
})
export class BudgetComponent implements OnInit, OnDestroy {
  budgets: IBudget[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected budgetService: BudgetService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.budgetService
      .query()
      .pipe(
        filter((res: HttpResponse<IBudget[]>) => res.ok),
        map((res: HttpResponse<IBudget[]>) => res.body)
      )
      .subscribe(
        (res: IBudget[]) => {
          this.budgets = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBudgets();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBudget) {
    return item.id;
  }

  registerChangeInBudgets() {
    this.eventSubscriber = this.eventManager.subscribe('budgetListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
