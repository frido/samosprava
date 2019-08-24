/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { ElectionUpdateComponent } from 'app/entities/election/election-update.component';
import { ElectionService } from 'app/entities/election/election.service';
import { Election } from 'app/shared/model/election.model';

describe('Component Tests', () => {
  describe('Election Management Update Component', () => {
    let comp: ElectionUpdateComponent;
    let fixture: ComponentFixture<ElectionUpdateComponent>;
    let service: ElectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [ElectionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ElectionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ElectionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ElectionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Election('123');
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
        const entity = new Election();
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
