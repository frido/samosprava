import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICouncilRelation } from 'app/shared/model/council-relation.model';

@Component({
  selector: 'jhi-council-relation-detail',
  templateUrl: './council-relation-detail.component.html'
})
export class CouncilRelationDetailComponent implements OnInit {
  councilRelation: ICouncilRelation;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ councilRelation }) => {
      this.councilRelation = councilRelation;
    });
  }

  previousState() {
    window.history.back();
  }
}
