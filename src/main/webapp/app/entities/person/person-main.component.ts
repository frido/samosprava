import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPerson } from 'app/shared/model/person.model';

@Component({
  selector: 'jhi-person-main',
  templateUrl: './person-main.component.html'
})
export class PersonMainComponent implements OnInit {
  person: IPerson;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ person }) => {
      this.person = person;
    });
  }

  previousState() {
    window.history.back();
  }
}
