/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { DepartmentRelationDetailComponent } from 'app/entities/department-relation/department-relation-detail.component';
import { DepartmentRelation } from 'app/shared/model/department-relation.model';

describe('Component Tests', () => {
  describe('DepartmentRelation Management Detail Component', () => {
    let comp: DepartmentRelationDetailComponent;
    let fixture: ComponentFixture<DepartmentRelationDetailComponent>;
    const route = ({ data: of({ departmentRelation: new DepartmentRelation('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [DepartmentRelationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DepartmentRelationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepartmentRelationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.departmentRelation).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
