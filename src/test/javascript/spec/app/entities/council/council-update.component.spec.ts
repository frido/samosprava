/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { CouncilUpdateComponent } from 'app/entities/council/council-update.component';
import { CouncilService } from 'app/entities/council/council.service';
import { Council } from 'app/shared/model/council.model';

describe('Component Tests', () => {
  describe('Council Management Update Component', () => {
    let comp: CouncilUpdateComponent;
    let fixture: ComponentFixture<CouncilUpdateComponent>;
    let service: CouncilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [CouncilUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CouncilUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CouncilUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CouncilService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Council('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Council();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
