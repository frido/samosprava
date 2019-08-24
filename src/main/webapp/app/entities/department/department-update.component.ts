import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDepartment, Department } from 'app/shared/model/department.model';
import { DepartmentService } from './department.service';
import { ICouncil } from 'app/shared/model/council.model';
import { CouncilService } from 'app/entities/council';

@Component({
  selector: 'jhi-department-update',
  templateUrl: './department-update.component.html'
})
export class DepartmentUpdateComponent implements OnInit {
  isSaving: boolean;

  councils: ICouncil[];

  editForm = this.fb.group({
    id: [],
    name: [],
    council: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected departmentService: DepartmentService,
    protected councilService: CouncilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ department }) => {
      this.updateForm(department);
    });
    this.councilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICouncil[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICouncil[]>) => response.body)
      )
      .subscribe((res: ICouncil[]) => (this.councils = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(department: IDepartment) {
    this.editForm.patchValue({
      id: department.id,
      name: department.name,
      council: department.council
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const department = this.createFromForm();
    if (department.id !== undefined) {
      this.subscribeToSaveResponse(this.departmentService.update(department));
    } else {
      this.subscribeToSaveResponse(this.departmentService.create(department));
    }
  }

  private createFromForm(): IDepartment {
    return {
      ...new Department(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      council: this.editForm.get(['council']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartment>>) {
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
