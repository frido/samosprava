import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDeputyRelation, DeputyRelation } from 'app/shared/model/deputy-relation.model';
import { DeputyRelationService } from './deputy-relation.service';
import { IVote } from 'app/shared/model/vote.model';
import { VoteService } from 'app/entities/vote';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';
import { ICouncil } from 'app/shared/model/council.model';
import { CouncilService } from 'app/entities/council';

@Component({
  selector: 'jhi-deputy-relation-update',
  templateUrl: './deputy-relation-update.component.html'
})
export class DeputyRelationUpdateComponent implements OnInit {
  isSaving: boolean;

  votes: IVote[];

  people: IPerson[];

  councils: ICouncil[];
  fromDp: any;
  toDp: any;

  editForm = this.fb.group({
    id: [],
    from: [],
    to: [],
    vote: [null, Validators.required],
    person: [null, Validators.required],
    council: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected deputyRelationService: DeputyRelationService,
    protected voteService: VoteService,
    protected personService: PersonService,
    protected councilService: CouncilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ deputyRelation }) => {
      this.updateForm(deputyRelation);
    });
    this.voteService
      .query({ filter: 'deputy-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IVote[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVote[]>) => response.body)
      )
      .subscribe(
        (res: IVote[]) => {
          if (!this.editForm.get('vote').value || !this.editForm.get('vote').value.id) {
            this.votes = res;
          } else {
            this.voteService
              .find(this.editForm.get('vote').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IVote>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IVote>) => subResponse.body)
              )
              .subscribe(
                (subRes: IVote) => (this.votes = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.personService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerson[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerson[]>) => response.body)
      )
      .subscribe((res: IPerson[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.councilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICouncil[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICouncil[]>) => response.body)
      )
      .subscribe((res: ICouncil[]) => (this.councils = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(deputyRelation: IDeputyRelation) {
    this.editForm.patchValue({
      id: deputyRelation.id,
      from: deputyRelation.from,
      to: deputyRelation.to,
      vote: deputyRelation.vote,
      person: deputyRelation.person,
      council: deputyRelation.council
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const deputyRelation = this.createFromForm();
    if (deputyRelation.id !== undefined) {
      this.subscribeToSaveResponse(this.deputyRelationService.update(deputyRelation));
    } else {
      this.subscribeToSaveResponse(this.deputyRelationService.create(deputyRelation));
    }
  }

  private createFromForm(): IDeputyRelation {
    return {
      ...new DeputyRelation(),
      id: this.editForm.get(['id']).value,
      from: this.editForm.get(['from']).value,
      to: this.editForm.get(['to']).value,
      vote: this.editForm.get(['vote']).value,
      person: this.editForm.get(['person']).value,
      council: this.editForm.get(['council']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeputyRelation>>) {
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

  trackVoteById(index: number, item: IVote) {
    return item.id;
  }

  trackPersonById(index: number, item: IPerson) {
    return item.id;
  }

  trackCouncilById(index: number, item: ICouncil) {
    return item.id;
  }
}
