import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICouncil, Council } from 'app/shared/model/council.model';
import { CouncilService } from './council.service';

@Component({
  selector: 'jhi-council-update',
  templateUrl: './council-update.component.html'
})
export class CouncilUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    key: [],
    name: [],
    deputyTitle: [],
    mayorTitle: [],
    officeTitle: [],
    fbTitle: [],
    fbLink: []
  });

  constructor(protected councilService: CouncilService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ council }) => {
      this.updateForm(council);
    });
  }

  updateForm(council: ICouncil) {
    this.editForm.patchValue({
      id: council.id,
      key: council.key,
      name: council.name,
      deputyTitle: council.deputyTitle,
      mayorTitle: council.mayorTitle,
      officeTitle: council.officeTitle,
      fbTitle: council.fbTitle,
      fbLink: council.fbLink
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const council = this.createFromForm();
    if (council.id !== undefined) {
      this.subscribeToSaveResponse(this.councilService.update(council));
    } else {
      this.subscribeToSaveResponse(this.councilService.create(council));
    }
  }

  private createFromForm(): ICouncil {
    return {
      ...new Council(),
      id: this.editForm.get(['id']).value,
      key: this.editForm.get(['key']).value,
      name: this.editForm.get(['name']).value,
      deputyTitle: this.editForm.get(['deputyTitle']).value,
      mayorTitle: this.editForm.get(['mayorTitle']).value,
      officeTitle: this.editForm.get(['officeTitle']).value,
      fbTitle: this.editForm.get(['fbTitle']).value,
      fbLink: this.editForm.get(['fbLink']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICouncil>>) {
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
