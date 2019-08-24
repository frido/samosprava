/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SamospravaTestModule } from '../../../test.module';
import { DeputyRelationDeleteDialogComponent } from 'app/entities/deputy-relation/deputy-relation-delete-dialog.component';
import { DeputyRelationService } from 'app/entities/deputy-relation/deputy-relation.service';

describe('Component Tests', () => {
  describe('DeputyRelation Management Delete Component', () => {
    let comp: DeputyRelationDeleteDialogComponent;
    let fixture: ComponentFixture<DeputyRelationDeleteDialogComponent>;
    let service: DeputyRelationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [DeputyRelationDeleteDialogComponent]
      })
        .overrideTemplate(DeputyRelationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeputyRelationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeputyRelationService);
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
