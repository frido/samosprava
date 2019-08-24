import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICouncilRelation } from 'app/shared/model/council-relation.model';
import { CouncilRelationService } from './council-relation.service';

@Component({
  selector: 'jhi-council-relation-delete-dialog',
  templateUrl: './council-relation-delete-dialog.component.html'
})
export class CouncilRelationDeleteDialogComponent {
  councilRelation: ICouncilRelation;

  constructor(
    protected councilRelationService: CouncilRelationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.councilRelationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'councilRelationListModification',
        content: 'Deleted an councilRelation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-council-relation-delete-popup',
  template: ''
})
export class CouncilRelationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ councilRelation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CouncilRelationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.councilRelation = councilRelation;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/council-relation', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/council-relation', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
