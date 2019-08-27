import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IResolution, Resolution } from 'app/shared/model/resolution.model';
import { ResolutionService } from './resolution.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';
import { ICouncil } from 'app/shared/model/council.model';
import { CouncilService } from 'app/entities/council';
import { IMeeting } from 'app/shared/model/meeting.model';
import { MeetingService } from 'app/entities/meeting';
import { Decision } from 'app/shared/model/decision.model';

@Component({
  selector: 'jhi-resolution-update',
  templateUrl: './resolution-update.component.html'
})
export class ResolutionUpdateComponent implements OnInit {
  isSaving: boolean;

  people: IPerson[];

  councils: ICouncil[];

  meetings: IMeeting[];

  resolution: IResolution;

  editForm = this.fb.group({
    id: [],
    number: [],
    type: [],
    title: [],
    description: [],
    voteFor: [],
    voteAgainst: [],
    presented: [],
    source: [],
    creators: [],
    voteFors: [],
    voteAgainsts: [],
    council: [null, Validators.required],
    meeting: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected resolutionService: ResolutionService,
    protected personService: PersonService,
    protected councilService: CouncilService,
    protected meetingService: MeetingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ resolution }) => {
      this.updateForm(resolution);
    });
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
    this.meetingService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMeeting[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMeeting[]>) => response.body)
      )
      .subscribe((res: IMeeting[]) => (this.meetings = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(resolution: IResolution) {
    this.editForm.patchValue({
      id: resolution.id,
      number: resolution.number,
      type: resolution.type,
      title: resolution.title,
      description: resolution.description,
      voteFor: resolution.voteFor,
      voteAgainst: resolution.voteAgainst,
      presented: resolution.presented,
      source: resolution.source,
      creators: resolution.creators,
      voteFors: resolution.voteFors,
      voteAgainsts: resolution.voteAgainsts,
      council: resolution.council,
      meeting: resolution.meeting
    });
    this.resolution = resolution;
  }

  previousState() {
    window.history.back();
  }

  addDecision() {
    if (!this.resolution.decisions) {
      this.resolution.decisions = [];
    }
    this.resolution.decisions.push(new Decision());
  }

  save() {
    this.isSaving = true;
    const resolution = this.createFromForm();
    if (resolution.id !== undefined) {
      this.subscribeToSaveResponse(this.resolutionService.update(resolution));
    } else {
      this.subscribeToSaveResponse(this.resolutionService.create(resolution));
    }
  }

  private createFromForm(): IResolution {
    return {
      ...new Resolution(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      type: this.editForm.get(['type']).value,
      title: this.editForm.get(['title']).value,
      description: this.editForm.get(['description']).value,
      voteFor: this.editForm.get(['voteFor']).value,
      voteAgainst: this.editForm.get(['voteAgainst']).value,
      presented: this.editForm.get(['presented']).value,
      source: this.editForm.get(['source']).value,
      creators: this.editForm.get(['creators']).value,
      voteFors: this.editForm.get(['voteFors']).value,
      voteAgainsts: this.editForm.get(['voteAgainsts']).value,
      council: this.editForm.get(['council']).value,
      meeting: this.editForm.get(['meeting']).value,
      // TODO: quick fix
      decisions: this.resolution.decisions
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResolution>>) {
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

  trackPersonById(index: number, item: IPerson) {
    return item.id;
  }

  trackCouncilById(index: number, item: ICouncil) {
    return item.id;
  }

  trackMeetingById(index: number, item: IMeeting) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
