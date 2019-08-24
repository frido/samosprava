import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICouncil } from 'app/shared/model/council.model';

@Component({
  selector: 'jhi-council-detail',
  templateUrl: './council-detail.component.html'
})
export class CouncilDetailComponent implements OnInit {
  council: ICouncil;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ council }) => {
      this.council = council;
    });
  }

  previousState() {
    window.history.back();
  }
}
