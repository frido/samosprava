/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SamospravaTestModule } from '../../../test.module';
import { ResolutionComponent } from 'app/entities/resolution/resolution.component';
import { ResolutionService } from 'app/shared/resolution.service';
import { Resolution } from 'app/shared/model/resolution.model';

describe('Component Tests', () => {
  describe('Resolution Management Component', () => {
    let comp: ResolutionComponent;
    let fixture: ComponentFixture<ResolutionComponent>;
    let service: ResolutionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [ResolutionComponent],
        providers: []
      })
        .overrideTemplate(ResolutionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResolutionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResolutionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Resolution('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.resolutions[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
