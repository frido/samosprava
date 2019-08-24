import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICommissionRelation, CommissionRelation } from 'app/shared/model/commission-relation.model';
import { CommissionRelationService } from './commission-relation.service';
import { ICommission } from 'app/shared/model/commission.model';
import { CommissionService } from 'app/entities/commission';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';

@Component({
  selector: 'jhi-commission-relation-update',
  templateUrl: './commission-relation-update.component.html'
})
export class CommissionRelationUpdateComponent implements OnInit {
  isSaving: boolean;

  commissions: ICommission[];

  people: IPerson[];
  fromDp: any;
  toDp: any;

  editForm = this.fb.group({
    id: [],
    from: [],
    to: [],
    type: [],
    commission: [null, Validators.required],
    person: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected commissionRelationService: CommissionRelationService,
    protected commissionService: CommissionService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ commissionRelation }) => {
      this.updateForm(commissionRelation);
    });
    this.commissionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICommission[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICommission[]>) => response.body)
      )
      .subscribe((res: ICommission[]) => (this.commissions = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.personService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerson[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerson[]>) => response.body)
      )
      .subscribe((res: IPerson[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(commissionRelation: ICommissionRelation) {
    this.editForm.patchValue({
      id: commissionRelation.id,
      from: commissionRelation.from,
      to: commissionRelation.to,
      type: commissionRelation.type,
      commission: commissionRelation.commission,
      person: commissionRelation.person
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const commissionRelation = this.createFromForm();
    if (commissionRelation.id !== undefined) {
      this.subscribeToSaveResponse(this.commissionRelationService.update(commissionRelation));
    } else {
      this.subscribeToSaveResponse(this.commissionRelationService.create(commissionRelation));
    }
  }

  private createFromForm(): ICommissionRelation {
    return {
      ...new CommissionRelation(),
      id: this.editForm.get(['id']).value,
      from: this.editForm.get(['from']).value,
      to: this.editForm.get(['to']).value,
      type: this.editForm.get(['type']).value,
      commission: this.editForm.get(['commission']).value,
      person: this.editForm.get(['person']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommissionRelation>>) {
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

  trackCommissionById(index: number, item: ICommission) {
    return item.id;
  }

  trackPersonById(index: number, item: IPerson) {
    return item.id;
  }
}
