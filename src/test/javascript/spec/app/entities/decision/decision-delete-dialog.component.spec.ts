/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SamospravaTestModule } from '../../../test.module';
import { DecisionDeleteDialogComponent } from 'app/entities/decision/decision-delete-dialog.component';
import { DecisionService } from 'app/entities/decision/decision.service';

describe('Component Tests', () => {
  describe('Decision Management Delete Component', () => {
    let comp: DecisionDeleteDialogComponent;
    let fixture: ComponentFixture<DecisionDeleteDialogComponent>;
    let service: DecisionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [DecisionDeleteDialogComponent]
      })
        .overrideTemplate(DecisionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DecisionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DecisionService);
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
