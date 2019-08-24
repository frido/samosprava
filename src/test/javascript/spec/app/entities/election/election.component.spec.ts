/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SamospravaTestModule } from '../../../test.module';
import { ElectionComponent } from 'app/entities/election/election.component';
import { ElectionService } from 'app/entities/election/election.service';
import { Election } from 'app/shared/model/election.model';

describe('Component Tests', () => {
  describe('Election Management Component', () => {
    let comp: ElectionComponent;
    let fixture: ComponentFixture<ElectionComponent>;
    let service: ElectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [ElectionComponent],
        providers: []
      })
        .overrideTemplate(ElectionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ElectionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ElectionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Election('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.elections[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
