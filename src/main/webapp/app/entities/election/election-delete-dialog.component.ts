import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IElection } from 'app/shared/model/election.model';
import { ElectionService } from './election.service';

@Component({
  selector: 'jhi-election-delete-dialog',
  templateUrl: './election-delete-dialog.component.html'
})
export class ElectionDeleteDialogComponent {
  election: IElection;

  constructor(protected electionService: ElectionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.electionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'electionListModification',
        content: 'Deleted an election'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-election-delete-popup',
  template: ''
})
export class ElectionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ election }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ElectionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.election = election;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/election', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/election', { outlets: { popup: null } }]);
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
