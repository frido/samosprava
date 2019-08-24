import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IVote, Vote } from 'app/shared/model/vote.model';
import { VoteService } from './vote.service';
import { IDeputyRelation } from 'app/shared/model/deputy-relation.model';
import { DeputyRelationService } from 'app/entities/deputy-relation';
import { IElection } from 'app/shared/model/election.model';
import { ElectionService } from 'app/entities/election';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';

@Component({
  selector: 'jhi-vote-update',
  templateUrl: './vote-update.component.html'
})
export class VoteUpdateComponent implements OnInit {
  isSaving: boolean;

  deputyrelations: IDeputyRelation[];

  elections: IElection[];

  people: IPerson[];

  editForm = this.fb.group({
    id: [],
    party: [],
    votes: [],
    election: [null, Validators.required],
    person: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected voteService: VoteService,
    protected deputyRelationService: DeputyRelationService,
    protected electionService: ElectionService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vote }) => {
      this.updateForm(vote);
    });
    this.deputyRelationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDeputyRelation[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDeputyRelation[]>) => response.body)
      )
      .subscribe((res: IDeputyRelation[]) => (this.deputyrelations = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.electionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IElection[]>) => mayBeOk.ok),
        map((response: HttpResponse<IElection[]>) => response.body)
      )
      .subscribe((res: IElection[]) => (this.elections = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.personService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerson[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerson[]>) => response.body)
      )
      .subscribe((res: IPerson[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(vote: IVote) {
    this.editForm.patchValue({
      id: vote.id,
      party: vote.party,
      votes: vote.votes,
      election: vote.election,
      person: vote.person
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vote = this.createFromForm();
    if (vote.id !== undefined) {
      this.subscribeToSaveResponse(this.voteService.update(vote));
    } else {
      this.subscribeToSaveResponse(this.voteService.create(vote));
    }
  }

  private createFromForm(): IVote {
    return {
      ...new Vote(),
      id: this.editForm.get(['id']).value,
      party: this.editForm.get(['party']).value,
      votes: this.editForm.get(['votes']).value,
      election: this.editForm.get(['election']).value,
      person: this.editForm.get(['person']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVote>>) {
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

  trackDeputyRelationById(index: number, item: IDeputyRelation) {
    return item.id;
  }

  trackElectionById(index: number, item: IElection) {
    return item.id;
  }

  trackPersonById(index: number, item: IPerson) {
    return item.id;
  }
}
