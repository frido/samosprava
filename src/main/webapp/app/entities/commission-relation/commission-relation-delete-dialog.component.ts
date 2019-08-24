import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommissionRelation } from 'app/shared/model/commission-relation.model';
import { CommissionRelationService } from './commission-relation.service';

@Component({
  selector: 'jhi-commission-relation-delete-dialog',
  templateUrl: './commission-relation-delete-dialog.component.html'
})
export class CommissionRelationDeleteDialogComponent {
  commissionRelation: ICommissionRelation;

  constructor(
    protected commissionRelationService: CommissionRelationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.commissionRelationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'commissionRelationListModification',
        content: 'Deleted an commissionRelation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-commission-relation-delete-popup',
  template: ''
})
export class CommissionRelationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ commissionRelation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CommissionRelationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.commissionRelation = commissionRelation;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/commission-relation', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/commission-relation', { outlets: { popup: null } }]);
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
