/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SamospravaTestModule } from '../../../test.module';
import { DepartmentRelationDeleteDialogComponent } from 'app/entities/department-relation/department-relation-delete-dialog.component';
import { DepartmentRelationService } from 'app/entities/department-relation/department-relation.service';

describe('Component Tests', () => {
  describe('DepartmentRelation Management Delete Component', () => {
    let comp: DepartmentRelationDeleteDialogComponent;
    let fixture: ComponentFixture<DepartmentRelationDeleteDialogComponent>;
    let service: DepartmentRelationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [DepartmentRelationDeleteDialogComponent]
      })
        .overrideTemplate(DepartmentRelationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepartmentRelationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepartmentRelationService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('123');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('123');
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
