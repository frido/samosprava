/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { DepartmentRelationUpdateComponent } from 'app/entities/department-relation/department-relation-update.component';
import { DepartmentRelationService } from 'app/entities/department-relation/department-relation.service';
import { DepartmentRelation } from 'app/shared/model/department-relation.model';

describe('Component Tests', () => {
  describe('DepartmentRelation Management Update Component', () => {
    let comp: DepartmentRelationUpdateComponent;
    let fixture: ComponentFixture<DepartmentRelationUpdateComponent>;
    let service: DepartmentRelationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [DepartmentRelationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DepartmentRelationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DepartmentRelationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepartmentRelationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DepartmentRelation('123');
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
        const entity = new DepartmentRelation();
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
