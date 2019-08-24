import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDeputyRelation } from 'app/shared/model/deputy-relation.model';
import { AccountService } from 'app/core';
import { DeputyRelationService } from './deputy-relation.service';

@Component({
  selector: 'jhi-deputy-relation',
  templateUrl: './deputy-relation.component.html'
})
export class DeputyRelationComponent implements OnInit, OnDestroy {
  deputyRelations: IDeputyRelation[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected deputyRelationService: DeputyRelationService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.deputyRelationService
      .query()
      .pipe(
        filter((res: HttpResponse<IDeputyRelation[]>) => res.ok),
        map((res: HttpResponse<IDeputyRelation[]>) => res.body)
      )
      .subscribe(
        (res: IDeputyRelation[]) => {
          this.deputyRelations = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDeputyRelations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDeputyRelation) {
    return item.id;
  }

  registerChangeInDeputyRelations() {
    this.eventSubscriber = this.eventManager.subscribe('deputyRelationListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
