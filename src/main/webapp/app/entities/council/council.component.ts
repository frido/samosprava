import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICouncil } from 'app/shared/model/council.model';
import { AccountService } from 'app/core';
import { CouncilService } from './council.service';

@Component({
  selector: 'jhi-council',
  templateUrl: './council.component.html'
})
export class CouncilComponent implements OnInit, OnDestroy {
  councils: ICouncil[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected councilService: CouncilService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.councilService
      .query()
      .pipe(
        filter((res: HttpResponse<ICouncil[]>) => res.ok),
        map((res: HttpResponse<ICouncil[]>) => res.body)
      )
      .subscribe(
        (res: ICouncil[]) => {
          this.councils = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCouncils();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICouncil) {
    return item.id;
  }

  registerChangeInCouncils() {
    this.eventSubscriber = this.eventManager.subscribe('councilListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
