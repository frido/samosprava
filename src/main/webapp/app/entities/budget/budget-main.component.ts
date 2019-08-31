import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBudget } from 'app/shared/model/budget.model';

@Component({
  selector: 'jhi-budget-main',
  templateUrl: './budget-main.component.html'
})
export class BudgetMainComponent implements OnInit {
  budget: IBudget;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ budget }) => {
      this.budget = budget;
    });
  }

  previousState() {
    window.history.back();
  }
}
