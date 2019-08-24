import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommission } from 'app/shared/model/commission.model';
import { CommissionService } from './commission.service';

@Component({
  selector: 'jhi-commission-delete-dialog',
  templateUrl: './commission-delete-dialog.component.html'
})
export class CommissionDeleteDialogComponent {
  commission: ICommission;

  constructor(
    protected commissionService: CommissionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.commissionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'commissionListModification',
        content: 'Deleted an commission'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-commission-delete-popup',
  template: ''
})
export class CommissionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ commission }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CommissionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.commission = commission;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/commission', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/commission', { outlets: { popup: null } }]);
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
