/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SamospravaTestModule } from '../../../test.module';
import { CommissionRelationDeleteDialogComponent } from 'app/entities/commission-relation/commission-relation-delete-dialog.component';
import { CommissionRelationService } from 'app/entities/commission-relation/commission-relation.service';

describe('Component Tests', () => {
  describe('CommissionRelation Management Delete Component', () => {
    let comp: CommissionRelationDeleteDialogComponent;
    let fixture: ComponentFixture<CommissionRelationDeleteDialogComponent>;
    let service: CommissionRelationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [CommissionRelationDeleteDialogComponent]
      })
        .overrideTemplate(CommissionRelationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommissionRelationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommissionRelationService);
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
