/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { ElectionDetailComponent } from 'app/entities/election/election-detail.component';
import { Election } from 'app/shared/model/election.model';

describe('Component Tests', () => {
  describe('Election Management Detail Component', () => {
    let comp: ElectionDetailComponent;
    let fixture: ComponentFixture<ElectionDetailComponent>;
    const route = ({ data: of({ election: new Election('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [ElectionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ElectionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ElectionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.election).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
