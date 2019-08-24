import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IResolution, Resolution } from 'app/shared/model/resolution.model';
import { ResolutionService } from './resolution.service';
import { IMeeting } from 'app/shared/model/meeting.model';
import { MeetingService } from 'app/entities/meeting';

@Component({
  selector: 'jhi-resolution-update',
  templateUrl: './resolution-update.component.html'
})
export class ResolutionUpdateComponent implements OnInit {
  isSaving: boolean;

  meetings: IMeeting[];

  editForm = this.fb.group({
    id: [],
    key: [],
    number: [],
    type: [],
    councilKey: [],
    creatorKey: [],
    title: [],
    description: [],
    voteFor: [],
    voteAgainst: [],
    presented: [],
    source: [],
    meeting: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected resolutionService: ResolutionService,
    protected meetingService: MeetingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ resolution }) => {
      this.updateForm(resolution);
    });
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
      key: resolution.key,
      number: resolution.number,
      type: resolution.type,
      councilKey: resolution.councilKey,
      creatorKey: resolution.creatorKey,
      title: resolution.title,
      description: resolution.description,
      voteFor: resolution.voteFor,
      voteAgainst: resolution.voteAgainst,
      presented: resolution.presented,
      source: resolution.source,
      meeting: resolution.meeting
    });
  }

  previousState() {
    window.history.back();
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
      key: this.editForm.get(['key']).value,
      number: this.editForm.get(['number']).value,
      type: this.editForm.get(['type']).value,
      councilKey: this.editForm.get(['councilKey']).value,
      creatorKey: this.editForm.get(['creatorKey']).value,
      title: this.editForm.get(['title']).value,
      description: this.editForm.get(['description']).value,
      voteFor: this.editForm.get(['voteFor']).value,
      voteAgainst: this.editForm.get(['voteAgainst']).value,
      presented: this.editForm.get(['presented']).value,
      source: this.editForm.get(['source']).value,
      meeting: this.editForm.get(['meeting']).value
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

  trackMeetingById(index: number, item: IMeeting) {
    return item.id;
  }
}
