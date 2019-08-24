import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDepartmentRelation, DepartmentRelation } from 'app/shared/model/department-relation.model';
import { DepartmentRelationService } from './department-relation.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';

@Component({
  selector: 'jhi-department-relation-update',
  templateUrl: './department-relation-update.component.html'
})
export class DepartmentRelationUpdateComponent implements OnInit {
  isSaving: boolean;

  departments: IDepartment[];

  people: IPerson[];
  fromDp: any;
  toDp: any;

  editForm = this.fb.group({
    id: [],
    from: [],
    to: [],
    type: [],
    department: [null, Validators.required],
    person: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected departmentRelationService: DepartmentRelationService,
    protected departmentService: DepartmentService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ departmentRelation }) => {
      this.updateForm(departmentRelation);
    });
    this.departmentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDepartment[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDepartment[]>) => response.body)
      )
      .subscribe((res: IDepartment[]) => (this.departments = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.personService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerson[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerson[]>) => response.body)
      )
      .subscribe((res: IPerson[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(departmentRelation: IDepartmentRelation) {
    this.editForm.patchValue({
      id: departmentRelation.id,
      from: departmentRelation.from,
      to: departmentRelation.to,
      type: departmentRelation.type,
      department: departmentRelation.department,
      person: departmentRelation.person
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const departmentRelation = this.createFromForm();
    if (departmentRelation.id !== undefined) {
      this.subscribeToSaveResponse(this.departmentRelationService.update(departmentRelation));
    } else {
      this.subscribeToSaveResponse(this.departmentRelationService.create(departmentRelation));
    }
  }

  private createFromForm(): IDepartmentRelation {
    return {
      ...new DepartmentRelation(),
      id: this.editForm.get(['id']).value,
      from: this.editForm.get(['from']).value,
      to: this.editForm.get(['to']).value,
      type: this.editForm.get(['type']).value,
      department: this.editForm.get(['department']).value,
      person: this.editForm.get(['person']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartmentRelation>>) {
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

  trackDepartmentById(index: number, item: IDepartment) {
    return item.id;
  }

  trackPersonById(index: number, item: IPerson) {
    return item.id;
  }
}
