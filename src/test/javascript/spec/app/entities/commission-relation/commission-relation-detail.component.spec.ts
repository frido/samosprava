/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { CommissionRelationDetailComponent } from 'app/entities/commission-relation/commission-relation-detail.component';
import { CommissionRelation } from 'app/shared/model/commission-relation.model';

describe('Component Tests', () => {
  describe('CommissionRelation Management Detail Component', () => {
    let comp: CommissionRelationDetailComponent;
    let fixture: ComponentFixture<CommissionRelationDetailComponent>;
    const route = ({ data: of({ commissionRelation: new CommissionRelation('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [CommissionRelationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CommissionRelationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommissionRelationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commissionRelation).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
