import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICommission } from 'app/shared/model/commission.model';
import { AccountService } from 'app/core';
import { CommissionService } from './commission.service';

@Component({
  selector: 'jhi-commission',
  templateUrl: './commission.component.html'
})
export class CommissionComponent implements OnInit, OnDestroy {
  commissions: ICommission[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected commissionService: CommissionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.commissionService
      .query()
      .pipe(
        filter((res: HttpResponse<ICommission[]>) => res.ok),
        map((res: HttpResponse<ICommission[]>) => res.body)
      )
      .subscribe(
        (res: ICommission[]) => {
          this.commissions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCommissions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICommission) {
    return item.id;
  }

  registerChangeInCommissions() {
    this.eventSubscriber = this.eventManager.subscribe('commissionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
