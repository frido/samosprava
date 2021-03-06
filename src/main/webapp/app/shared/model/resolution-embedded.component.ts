import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IResolution } from 'app/shared/model/resolution.model';
import { AccountService } from 'app/core';
import { ResolutionService } from 'app/shared/resolution.service';
import { Council } from './council.model';

@Component({
  selector: 'jhi-resolution-embedded',
  templateUrl: './resolution-embedded.component.html'
})
export class ResolutionEmbeddedComponent implements OnInit, OnDestroy {
  resolutions: IResolution[];
  currentAccount: any;
  eventSubscriber: Subscription;
  @Input() council: Council;

  constructor(
    protected resolutionService: ResolutionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.resolutionService
      .query({ councilId: this.council.id })
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
