import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IMeeting, Meeting } from 'app/shared/model/meeting.model';
import { MeetingService } from './meeting.service';

@Component({
  selector: 'jhi-meeting-update',
  templateUrl: './meeting-update.component.html'
})
export class MeetingUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    key: [],
    councilKey: [],
    date: [],
    place: []
  });

  constructor(protected meetingService: MeetingService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ meeting }) => {
      this.updateForm(meeting);
    });
  }

  updateForm(meeting: IMeeting) {
    this.editForm.patchValue({
      id: meeting.id,
      key: meeting.key,
      councilKey: meeting.councilKey,
      date: meeting.date != null ? meeting.date.format(DATE_TIME_FORMAT) : null,
      place: meeting.place
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const meeting = this.createFromForm();
    if (meeting.id !== undefined) {
      this.subscribeToSaveResponse(this.meetingService.update(meeting));
    } else {
      this.subscribeToSaveResponse(this.meetingService.create(meeting));
    }
  }

  private createFromForm(): IMeeting {
    return {
      ...new Meeting(),
      id: this.editForm.get(['id']).value,
      key: this.editForm.get(['key']).value,
      councilKey: this.editForm.get(['councilKey']).value,
      date: this.editForm.get(['date']).value != null ? moment(this.editForm.get(['date']).value, DATE_TIME_FORMAT) : undefined,
      place: this.editForm.get(['place']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMeeting>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
