import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICouncilRelation, CouncilRelation } from 'app/shared/model/council-relation.model';
import { CouncilRelationService } from './council-relation.service';
import { ICouncil } from 'app/shared/model/council.model';
import { CouncilService } from 'app/entities/council';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';

@Component({
  selector: 'jhi-council-relation-update',
  templateUrl: './council-relation-update.component.html'
})
export class CouncilRelationUpdateComponent implements OnInit {
  isSaving: boolean;

  councils: ICouncil[];

  people: IPerson[];
  fromDp: any;
  toDp: any;

  editForm = this.fb.group({
    id: [],
    from: [],
    to: [],
    type: [],
    council: [null, Validators.required],
    person: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected councilRelationService: CouncilRelationService,
    protected councilService: CouncilService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ councilRelation }) => {
      this.updateForm(councilRelation);
    });
    this.councilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICouncil[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICouncil[]>) => response.body)
      )
      .subscribe((res: ICouncil[]) => (this.councils = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.personService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerson[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerson[]>) => response.body)
      )
      .subscribe((res: IPerson[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(councilRelation: ICouncilRelation) {
    this.editForm.patchValue({
      id: councilRelation.id,
      from: councilRelation.from,
      to: councilRelation.to,
      type: councilRelation.type,
      council: councilRelation.council,
      person: councilRelation.person
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const councilRelation = this.createFromForm();
    if (councilRelation.id !== undefined) {
      this.subscribeToSaveResponse(this.councilRelationService.update(councilRelation));
    } else {
      this.subscribeToSaveResponse(this.councilRelationService.create(councilRelation));
    }
  }

  private createFromForm(): ICouncilRelation {
    return {
      ...new CouncilRelation(),
      id: this.editForm.get(['id']).value,
      from: this.editForm.get(['from']).value,
      to: this.editForm.get(['to']).value,
      type: this.editForm.get(['type']).value,
      council: this.editForm.get(['council']).value,
      person: this.editForm.get(['person']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICouncilRelation>>) {
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

  trackPersonById(index: number, item: IPerson) {
    return item.id;
  }
}
