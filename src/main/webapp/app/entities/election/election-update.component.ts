import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IElection, Election } from 'app/shared/model/election.model';
import { ElectionService } from './election.service';
import { ICouncil } from 'app/shared/model/council.model';
import { CouncilService } from 'app/entities/council';

@Component({
  selector: 'jhi-election-update',
  templateUrl: './election-update.component.html'
})
export class ElectionUpdateComponent implements OnInit {
  isSaving: boolean;

  councils: ICouncil[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    date: [],
    type: [],
    council: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected electionService: ElectionService,
    protected councilService: CouncilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ election }) => {
      this.updateForm(election);
    });
    this.councilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICouncil[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICouncil[]>) => response.body)
      )
      .subscribe((res: ICouncil[]) => (this.councils = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(election: IElection) {
    this.editForm.patchValue({
      id: election.id,
      name: election.name,
      date: election.date,
      type: election.type,
      council: election.council
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const election = this.createFromForm();
    if (election.id !== undefined) {
      this.subscribeToSaveResponse(this.electionService.update(election));
    } else {
      this.subscribeToSaveResponse(this.electionService.create(election));
    }
  }

  private createFromForm(): IElection {
    return {
      ...new Election(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      date: this.editForm.get(['date']).value,
      type: this.editForm.get(['type']).value,
      council: this.editForm.get(['council']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IElection>>) {
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
