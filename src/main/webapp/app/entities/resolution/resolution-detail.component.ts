import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResolution } from 'app/shared/model/resolution.model';

@Component({
  selector: 'jhi-resolution-detail',
  templateUrl: './resolution-detail.component.html'
})
export class ResolutionDetailComponent implements OnInit {
  resolution: IResolution;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ resolution }) => {
      this.resolution = resolution;
    });
  }

  previousState() {
    window.history.back();
  }
}
