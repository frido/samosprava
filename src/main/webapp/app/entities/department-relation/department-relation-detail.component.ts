import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepartmentRelation } from 'app/shared/model/department-relation.model';

@Component({
  selector: 'jhi-department-relation-detail',
  templateUrl: './department-relation-detail.component.html'
})
export class DepartmentRelationDetailComponent implements OnInit {
  departmentRelation: IDepartmentRelation;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ departmentRelation }) => {
      this.departmentRelation = departmentRelation;
    });
  }

  previousState() {
    window.history.back();
  }
}
