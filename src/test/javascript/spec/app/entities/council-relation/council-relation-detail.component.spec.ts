/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { CouncilRelationDetailComponent } from 'app/entities/council-relation/council-relation-detail.component';
import { CouncilRelation } from 'app/shared/model/council-relation.model';

describe('Component Tests', () => {
  describe('CouncilRelation Management Detail Component', () => {
    let comp: CouncilRelationDetailComponent;
    let fixture: ComponentFixture<CouncilRelationDetailComponent>;
    const route = ({ data: of({ councilRelation: new CouncilRelation('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [CouncilRelationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CouncilRelationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CouncilRelationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.councilRelation).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
