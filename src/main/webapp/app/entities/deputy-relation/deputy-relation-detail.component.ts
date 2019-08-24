import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeputyRelation } from 'app/shared/model/deputy-relation.model';

@Component({
  selector: 'jhi-deputy-relation-detail',
  templateUrl: './deputy-relation-detail.component.html'
})
export class DeputyRelationDetailComponent implements OnInit {
  deputyRelation: IDeputyRelation;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deputyRelation }) => {
      this.deputyRelation = deputyRelation;
    });
  }

  previousState() {
    window.history.back();
  }
}
