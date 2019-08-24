import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDepartmentRelation } from 'app/shared/model/department-relation.model';
import { AccountService } from 'app/core';
import { DepartmentRelationService } from './department-relation.service';

@Component({
  selector: 'jhi-department-relation',
  templateUrl: './department-relation.component.html'
})
export class DepartmentRelationComponent implements OnInit, OnDestroy {
  departmentRelations: IDepartmentRelation[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected departmentRelationService: DepartmentRelationService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.departmentRelationService
      .query()
      .pipe(
        filter((res: HttpResponse<IDepartmentRelation[]>) => res.ok),
        map((res: HttpResponse<IDepartmentRelation[]>) => res.body)
      )
      .subscribe(
        (res: IDepartmentRelation[]) => {
          this.departmentRelations = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDepartmentRelations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDepartmentRelation) {
    return item.id;
  }

  registerChangeInDepartmentRelations() {
    this.eventSubscriber = this.eventManager.subscribe('departmentRelationListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
