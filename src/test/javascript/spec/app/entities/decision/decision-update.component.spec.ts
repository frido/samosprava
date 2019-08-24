/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { DecisionUpdateComponent } from 'app/entities/decision/decision-update.component';
import { DecisionService } from 'app/entities/decision/decision.service';
import { Decision } from 'app/shared/model/decision.model';

describe('Component Tests', () => {
  describe('Decision Management Update Component', () => {
    let comp: DecisionUpdateComponent;
    let fixture: ComponentFixture<DecisionUpdateComponent>;
    let service: DecisionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [DecisionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DecisionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DecisionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DecisionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Decision('123');
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
        const entity = new Decision();
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
