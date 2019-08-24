import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICommissionRelation } from 'app/shared/model/commission-relation.model';
import { AccountService } from 'app/core';
import { CommissionRelationService } from './commission-relation.service';

@Component({
  selector: 'jhi-commission-relation',
  templateUrl: './commission-relation.component.html'
})
export class CommissionRelationComponent implements OnInit, OnDestroy {
  commissionRelations: ICommissionRelation[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected commissionRelationService: CommissionRelationService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.commissionRelationService
      .query()
      .pipe(
        filter((res: HttpResponse<ICommissionRelation[]>) => res.ok),
        map((res: HttpResponse<ICommissionRelation[]>) => res.body)
      )
      .subscribe(
        (res: ICommissionRelation[]) => {
          this.commissionRelations = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCommissionRelations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICommissionRelation) {
    return item.id;
  }

  registerChangeInCommissionRelations() {
    this.eventSubscriber = this.eventManager.subscribe('commissionRelationListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
