import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICouncil, Council } from 'app/shared/model/council.model';
import { CouncilService } from './council.service';
import { IBudget } from 'app/shared/model/budget.model';
import { BudgetService } from 'app/entities/budget';

@Component({
  selector: 'jhi-council-update',
  templateUrl: './council-update.component.html'
})
export class CouncilUpdateComponent implements OnInit {
  isSaving: boolean;

  budgets: IBudget[];

  editForm = this.fb.group({
    id: [],
    key: [],
    name: [],
    deputyTitle: [],
    mayorTitle: [],
    officeTitle: [],
    fbTitle: [],
    fbLink: [],
    budget: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected councilService: CouncilService,
    protected budgetService: BudgetService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ council }) => {
      this.updateForm(council);
    });
    this.budgetService
      .query({ filter: 'council-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IBudget[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBudget[]>) => response.body)
      )
      .subscribe(
        (res: IBudget[]) => {
          if (!this.editForm.get('budget').value || !this.editForm.get('budget').value.id) {
            this.budgets = res;
          } else {
            this.budgetService
              .find(this.editForm.get('budget').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IBudget>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IBudget>) => subResponse.body)
              )
              .subscribe(
                (subRes: IBudget) => (this.budgets = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(council: ICouncil) {
    this.editForm.patchValue({
      id: council.id,
      key: council.key,
      name: council.name,
      deputyTitle: council.deputyTitle,
      mayorTitle: council.mayorTitle,
      officeTitle: council.officeTitle,
      fbTitle: council.fbTitle,
      fbLink: council.fbLink,
      budget: council.budget
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const council = this.createFromForm();
    if (council.id !== undefined) {
      this.subscribeToSaveResponse(this.councilService.update(council));
    } else {
      this.subscribeToSaveResponse(this.councilService.create(council));
    }
  }

  private createFromForm(): ICouncil {
    return {
      ...new Council(),
      id: this.editForm.get(['id']).value,
      key: this.editForm.get(['key']).value,
      name: this.editForm.get(['name']).value,
      deputyTitle: this.editForm.get(['deputyTitle']).value,
      mayorTitle: this.editForm.get(['mayorTitle']).value,
      officeTitle: this.editForm.get(['officeTitle']).value,
      fbTitle: this.editForm.get(['fbTitle']).value,
      fbLink: this.editForm.get(['fbLink']).value,
      budget: this.editForm.get(['budget']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICouncil>>) {
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

  trackBudgetById(index: number, item: IBudget) {
    return item.id;
  }
}
