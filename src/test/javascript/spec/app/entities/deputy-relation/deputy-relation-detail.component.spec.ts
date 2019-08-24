/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { DeputyRelationDetailComponent } from 'app/entities/deputy-relation/deputy-relation-detail.component';
import { DeputyRelation } from 'app/shared/model/deputy-relation.model';

describe('Component Tests', () => {
  describe('DeputyRelation Management Detail Component', () => {
    let comp: DeputyRelationDetailComponent;
    let fixture: ComponentFixture<DeputyRelationDetailComponent>;
    const route = ({ data: of({ deputyRelation: new DeputyRelation('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [DeputyRelationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DeputyRelationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeputyRelationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deputyRelation).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
