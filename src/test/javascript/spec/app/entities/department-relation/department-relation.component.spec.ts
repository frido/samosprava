/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SamospravaTestModule } from '../../../test.module';
import { DepartmentRelationComponent } from 'app/entities/department-relation/department-relation.component';
import { DepartmentRelationService } from 'app/entities/department-relation/department-relation.service';
import { DepartmentRelation } from 'app/shared/model/department-relation.model';

describe('Component Tests', () => {
  describe('DepartmentRelation Management Component', () => {
    let comp: DepartmentRelationComponent;
    let fixture: ComponentFixture<DepartmentRelationComponent>;
    let service: DepartmentRelationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [DepartmentRelationComponent],
        providers: []
      })
        .overrideTemplate(DepartmentRelationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DepartmentRelationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepartmentRelationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DepartmentRelation('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.departmentRelations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
