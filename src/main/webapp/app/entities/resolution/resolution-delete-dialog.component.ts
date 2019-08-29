import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResolution } from 'app/shared/model/resolution.model';
import { ResolutionService } from '../../shared/resolution.service';

@Component({
  selector: 'jhi-resolution-delete-dialog',
  templateUrl: './resolution-delete-dialog.component.html'
})
export class ResolutionDeleteDialogComponent {
  resolution: IResolution;

  constructor(
    protected resolutionService: ResolutionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.resolutionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'resolutionListModification',
        content: 'Deleted an resolution'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-resolution-delete-popup',
  template: ''
})
export class ResolutionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ resolution }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ResolutionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.resolution = resolution;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/resolution', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/resolution', { outlets: { popup: null } }]);
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
