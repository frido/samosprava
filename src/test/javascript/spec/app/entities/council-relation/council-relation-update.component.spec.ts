/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { CouncilRelationUpdateComponent } from 'app/entities/council-relation/council-relation-update.component';
import { CouncilRelationService } from 'app/entities/council-relation/council-relation.service';
import { CouncilRelation } from 'app/shared/model/council-relation.model';

describe('Component Tests', () => {
  describe('CouncilRelation Management Update Component', () => {
    let comp: CouncilRelationUpdateComponent;
    let fixture: ComponentFixture<CouncilRelationUpdateComponent>;
    let service: CouncilRelationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [CouncilRelationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CouncilRelationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CouncilRelationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CouncilRelationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CouncilRelation('123');
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
        const entity = new CouncilRelation();
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
