/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { ResolutionDetailComponent } from 'app/entities/resolution/resolution-detail.component';
import { Resolution } from 'app/shared/model/resolution.model';

describe('Component Tests', () => {
  describe('Resolution Management Detail Component', () => {
    let comp: ResolutionDetailComponent;
    let fixture: ComponentFixture<ResolutionDetailComponent>;
    const route = ({ data: of({ resolution: new Resolution('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [ResolutionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ResolutionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResolutionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.resolution).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
