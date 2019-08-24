import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeputyRelation } from 'app/shared/model/deputy-relation.model';
import { DeputyRelationService } from './deputy-relation.service';

@Component({
  selector: 'jhi-deputy-relation-delete-dialog',
  templateUrl: './deputy-relation-delete-dialog.component.html'
})
export class DeputyRelationDeleteDialogComponent {
  deputyRelation: IDeputyRelation;

  constructor(
    protected deputyRelationService: DeputyRelationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.deputyRelationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'deputyRelationListModification',
        content: 'Deleted an deputyRelation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-deputy-relation-delete-popup',
  template: ''
})
export class DeputyRelationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deputyRelation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DeputyRelationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.deputyRelation = deputyRelation;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/deputy-relation', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/deputy-relation', { outlets: { popup: null } }]);
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
