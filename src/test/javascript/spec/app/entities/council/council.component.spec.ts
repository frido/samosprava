/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SamospravaTestModule } from '../../../test.module';
import { CouncilComponent } from 'app/entities/council/council.component';
import { CouncilService } from 'app/entities/council/council.service';
import { Council } from 'app/shared/model/council.model';

describe('Component Tests', () => {
  describe('Council Management Component', () => {
    let comp: CouncilComponent;
    let fixture: ComponentFixture<CouncilComponent>;
    let service: CouncilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [CouncilComponent],
        providers: []
      })
        .overrideTemplate(CouncilComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CouncilComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CouncilService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Council('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.councils[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
