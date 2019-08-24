import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICouncilRelation } from 'app/shared/model/council-relation.model';
import { AccountService } from 'app/core';
import { CouncilRelationService } from './council-relation.service';

@Component({
  selector: 'jhi-council-relation',
  templateUrl: './council-relation.component.html'
})
export class CouncilRelationComponent implements OnInit, OnDestroy {
  councilRelations: ICouncilRelation[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected councilRelationService: CouncilRelationService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.councilRelationService
      .query()
      .pipe(
        filter((res: HttpResponse<ICouncilRelation[]>) => res.ok),
        map((res: HttpResponse<ICouncilRelation[]>) => res.body)
      )
      .subscribe(
        (res: ICouncilRelation[]) => {
          this.councilRelations = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCouncilRelations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICouncilRelation) {
    return item.id;
  }

  registerChangeInCouncilRelations() {
    this.eventSubscriber = this.eventManager.subscribe('councilRelationListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
