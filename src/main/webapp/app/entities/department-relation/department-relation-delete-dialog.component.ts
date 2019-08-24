import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepartmentRelation } from 'app/shared/model/department-relation.model';
import { DepartmentRelationService } from './department-relation.service';

@Component({
  selector: 'jhi-department-relation-delete-dialog',
  templateUrl: './department-relation-delete-dialog.component.html'
})
export class DepartmentRelationDeleteDialogComponent {
  departmentRelation: IDepartmentRelation;

  constructor(
    protected departmentRelationService: DepartmentRelationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.departmentRelationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'departmentRelationListModification',
        content: 'Deleted an departmentRelation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-department-relation-delete-popup',
  template: ''
})
export class DepartmentRelationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ departmentRelation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DepartmentRelationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.departmentRelation = departmentRelation;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/department-relation', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/department-relation', { outlets: { popup: null } }]);
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
