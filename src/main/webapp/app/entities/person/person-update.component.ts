import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPerson, Person } from 'app/shared/model/person.model';
import { PersonService } from './person.service';
import { IResolution } from 'app/shared/model/resolution.model';
import { ResolutionService } from 'app/entities/resolution';

@Component({
  selector: 'jhi-person-update',
  templateUrl: './person-update.component.html'
})
export class PersonUpdateComponent implements OnInit {
  isSaving: boolean;

  resolutions: IResolution[];

  editForm = this.fb.group({
    id: [],
    name: [],
    prefix: [],
    suffix: [],
    club: [],
    facebook: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected personService: PersonService,
    protected resolutionService: ResolutionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ person }) => {
      this.updateForm(person);
    });
    this.resolutionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IResolution[]>) => mayBeOk.ok),
        map((response: HttpResponse<IResolution[]>) => response.body)
      )
      .subscribe((res: IResolution[]) => (this.resolutions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(person: IPerson) {
    this.editForm.patchValue({
      id: person.id,
      name: person.name,
      prefix: person.prefix,
      suffix: person.suffix,
      club: person.club,
      facebook: person.facebook
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const person = this.createFromForm();
    if (person.id !== undefined) {
      this.subscribeToSaveResponse(this.personService.update(person));
    } else {
      this.subscribeToSaveResponse(this.personService.create(person));
    }
  }

  private createFromForm(): IPerson {
    return {
      ...new Person(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      prefix: this.editForm.get(['prefix']).value,
      suffix: this.editForm.get(['suffix']).value,
      club: this.editForm.get(['club']).value,
      facebook: this.editForm.get(['facebook']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerson>>) {
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

  trackResolutionById(index: number, item: IResolution) {
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
