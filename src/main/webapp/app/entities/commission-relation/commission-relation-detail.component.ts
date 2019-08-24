import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommissionRelation } from 'app/shared/model/commission-relation.model';

@Component({
  selector: 'jhi-commission-relation-detail',
  templateUrl: './commission-relation-detail.component.html'
})
export class CommissionRelationDetailComponent implements OnInit {
  commissionRelation: ICommissionRelation;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ commissionRelation }) => {
      this.commissionRelation = commissionRelation;
    });
  }

  previousState() {
    window.history.back();
  }
}
