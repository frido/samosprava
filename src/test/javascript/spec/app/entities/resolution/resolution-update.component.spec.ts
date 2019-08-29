/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { ResolutionUpdateComponent } from 'app/entities/resolution/resolution-update.component';
import { ResolutionService } from 'app/shared/resolution.service';
import { Resolution } from 'app/shared/model/resolution.model';

describe('Component Tests', () => {
  describe('Resolution Management Update Component', () => {
    let comp: ResolutionUpdateComponent;
    let fixture: ComponentFixture<ResolutionUpdateComponent>;
    let service: ResolutionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [ResolutionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ResolutionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResolutionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResolutionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Resolution('123');
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
        const entity = new Resolution();
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
