import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMeeting, Meeting } from 'app/shared/model/meeting.model';
import { MeetingService } from './meeting.service';
import { ICouncil } from 'app/shared/model/council.model';
import { CouncilService } from 'app/entities/council';

@Component({
  selector: 'jhi-meeting-update',
  templateUrl: './meeting-update.component.html'
})
export class MeetingUpdateComponent implements OnInit {
  isSaving: boolean;

  councils: ICouncil[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    place: [],
    council: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected meetingService: MeetingService,
    protected councilService: CouncilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ meeting }) => {
      this.updateForm(meeting);
    });
    this.councilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICouncil[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICouncil[]>) => response.body)
      )
      .subscribe((res: ICouncil[]) => (this.councils = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(meeting: IMeeting) {
    this.editForm.patchValue({
      id: meeting.id,
      date: meeting.date,
      place: meeting.place,
      council: meeting.council
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
      date: this.editForm.get(['date']).value,
      place: this.editForm.get(['place']).value,
      council: this.editForm.get(['council']).value
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
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCouncilById(index: number, item: ICouncil) {
    return item.id;
  }
}
