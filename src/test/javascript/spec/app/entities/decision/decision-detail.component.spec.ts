/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { DecisionDetailComponent } from 'app/entities/decision/decision-detail.component';
import { Decision } from 'app/shared/model/decision.model';

describe('Component Tests', () => {
  describe('Decision Management Detail Component', () => {
    let comp: DecisionDetailComponent;
    let fixture: ComponentFixture<DecisionDetailComponent>;
    const route = ({ data: of({ decision: new Decision('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [DecisionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DecisionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DecisionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.decision).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
