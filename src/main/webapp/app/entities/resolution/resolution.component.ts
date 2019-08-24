import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IResolution } from 'app/shared/model/resolution.model';
import { AccountService } from 'app/core';
import { ResolutionService } from './resolution.service';

@Component({
  selector: 'jhi-resolution',
  templateUrl: './resolution.component.html'
})
export class ResolutionComponent implements OnInit, OnDestroy {
  resolutions: IResolution[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected resolutionService: ResolutionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.resolutionService
      .query()
      .pipe(
        filter((res: HttpResponse<IResolution[]>) => res.ok),
        map((res: HttpResponse<IResolution[]>) => res.body)
      )
      .subscribe(
        (res: IResolution[]) => {
          this.resolutions = res;
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
