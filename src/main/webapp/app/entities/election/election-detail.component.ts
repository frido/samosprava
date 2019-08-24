import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IElection } from 'app/shared/model/election.model';

@Component({
  selector: 'jhi-election-detail',
  templateUrl: './election-detail.component.html'
})
export class ElectionDetailComponent implements OnInit {
  election: IElection;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ election }) => {
      this.election = election;
    });
  }

  previousState() {
    window.history.back();
  }
}
