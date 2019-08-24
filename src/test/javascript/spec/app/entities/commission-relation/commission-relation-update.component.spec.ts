/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { CommissionRelationUpdateComponent } from 'app/entities/commission-relation/commission-relation-update.component';
import { CommissionRelationService } from 'app/entities/commission-relation/commission-relation.service';
import { CommissionRelation } from 'app/shared/model/commission-relation.model';

describe('Component Tests', () => {
  describe('CommissionRelation Management Update Component', () => {
    let comp: CommissionRelationUpdateComponent;
    let fixture: ComponentFixture<CommissionRelationUpdateComponent>;
    let service: CommissionRelationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [CommissionRelationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CommissionRelationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommissionRelationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommissionRelationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommissionRelation('123');
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
        const entity = new CommissionRelation();
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
