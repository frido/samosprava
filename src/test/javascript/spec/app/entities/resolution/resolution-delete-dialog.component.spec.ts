/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SamospravaTestModule } from '../../../test.module';
import { ResolutionDeleteDialogComponent } from 'app/entities/resolution/resolution-delete-dialog.component';
import { ResolutionService } from 'app/entities/resolution/resolution.service';

describe('Component Tests', () => {
  describe('Resolution Management Delete Component', () => {
    let comp: ResolutionDeleteDialogComponent;
    let fixture: ComponentFixture<ResolutionDeleteDialogComponent>;
    let service: ResolutionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [ResolutionDeleteDialogComponent]
      })
        .overrideTemplate(ResolutionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResolutionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResolutionService);
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
