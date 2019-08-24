/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SamospravaTestModule } from '../../../test.module';
import { CouncilRelationDeleteDialogComponent } from 'app/entities/council-relation/council-relation-delete-dialog.component';
import { CouncilRelationService } from 'app/entities/council-relation/council-relation.service';

describe('Component Tests', () => {
  describe('CouncilRelation Management Delete Component', () => {
    let comp: CouncilRelationDeleteDialogComponent;
    let fixture: ComponentFixture<CouncilRelationDeleteDialogComponent>;
    let service: CouncilRelationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [CouncilRelationDeleteDialogComponent]
      })
        .overrideTemplate(CouncilRelationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CouncilRelationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CouncilRelationService);
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
