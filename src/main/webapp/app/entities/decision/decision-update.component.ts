import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDecision, Decision } from 'app/shared/model/decision.model';
import { DecisionService } from './decision.service';

@Component({
  selector: 'jhi-decision-update',
  templateUrl: './decision-update.component.html'
})
export class DecisionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    status: [],
    description: []
  });

  constructor(protected decisionService: DecisionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ decision }) => {
      this.updateForm(decision);
    });
  }

  updateForm(decision: IDecision) {
    this.editForm.patchValue({
      id: decision.id,
      status: decision.status,
      description: decision.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const decision = this.createFromForm();
    if (decision.id !== undefined) {
      this.subscribeToSaveResponse(this.decisionService.update(decision));
    } else {
      this.subscribeToSaveResponse(this.decisionService.create(decision));
    }
  }

  private createFromForm(): IDecision {
    return {
      ...new Decision(),
      id: this.editForm.get(['id']).value,
      status: this.editForm.get(['status']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDecision>>) {
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
