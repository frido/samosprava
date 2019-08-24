import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommission, Commission } from 'app/shared/model/commission.model';
import { CommissionService } from './commission.service';
import { ICouncil } from 'app/shared/model/council.model';
import { CouncilService } from 'app/entities/council';

@Component({
  selector: 'jhi-commission-update',
  templateUrl: './commission-update.component.html'
})
export class CommissionUpdateComponent implements OnInit {
  isSaving: boolean;

  councils: ICouncil[];

  editForm = this.fb.group({
    id: [],
    name: [],
    desc: [],
    council: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected commissionService: CommissionService,
    protected councilService: CouncilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ commission }) => {
      this.updateForm(commission);
    });
    this.councilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICouncil[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICouncil[]>) => response.body)
      )
      .subscribe((res: ICouncil[]) => (this.councils = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(commission: ICommission) {
    this.editForm.patchValue({
      id: commission.id,
      name: commission.name,
      desc: commission.desc,
      council: commission.council
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const commission = this.createFromForm();
    if (commission.id !== undefined) {
      this.subscribeToSaveResponse(this.commissionService.update(commission));
    } else {
      this.subscribeToSaveResponse(this.commissionService.create(commission));
    }
  }

  private createFromForm(): ICommission {
    return {
      ...new Commission(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      desc: this.editForm.get(['desc']).value,
      council: this.editForm.get(['council']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommission>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCouncilById(index: number, item: ICouncil) {
    return item.id;
  }
}
