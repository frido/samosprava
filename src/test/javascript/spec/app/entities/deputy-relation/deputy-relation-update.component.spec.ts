/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { DeputyRelationUpdateComponent } from 'app/entities/deputy-relation/deputy-relation-update.component';
import { DeputyRelationService } from 'app/entities/deputy-relation/deputy-relation.service';
import { DeputyRelation } from 'app/shared/model/deputy-relation.model';

describe('Component Tests', () => {
  describe('DeputyRelation Management Update Component', () => {
    let comp: DeputyRelationUpdateComponent;
    let fixture: ComponentFixture<DeputyRelationUpdateComponent>;
    let service: DeputyRelationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [DeputyRelationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DeputyRelationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeputyRelationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeputyRelationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeputyRelation('123');
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
        const entity = new DeputyRelation();
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
