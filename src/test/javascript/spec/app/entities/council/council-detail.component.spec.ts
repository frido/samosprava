/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { CouncilDetailComponent } from 'app/entities/council/council-detail.component';
import { Council } from 'app/shared/model/council.model';

describe('Component Tests', () => {
  describe('Council Management Detail Component', () => {
    let comp: CouncilDetailComponent;
    let fixture: ComponentFixture<CouncilDetailComponent>;
    const route = ({ data: of({ council: new Council('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [CouncilDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CouncilDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CouncilDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.council).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
