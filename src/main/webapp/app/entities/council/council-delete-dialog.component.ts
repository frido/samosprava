import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICouncil } from 'app/shared/model/council.model';
import { CouncilService } from './council.service';

@Component({
  selector: 'jhi-council-delete-dialog',
  templateUrl: './council-delete-dialog.component.html'
})
export class CouncilDeleteDialogComponent {
  council: ICouncil;

  constructor(protected councilService: CouncilService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.councilService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'councilListModification',
        content: 'Deleted an council'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-council-delete-popup',
  template: ''
})
export class CouncilDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ council }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CouncilDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.council = council;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/council', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/council', { outlets: { popup: null } }]);
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
