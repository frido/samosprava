/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SamospravaTestModule } from '../../../test.module';
import { DecisionComponent } from 'app/entities/decision/decision.component';
import { DecisionService } from 'app/entities/decision/decision.service';
import { Decision } from 'app/shared/model/decision.model';

describe('Component Tests', () => {
  describe('Decision Management Component', () => {
    let comp: DecisionComponent;
    let fixture: ComponentFixture<DecisionComponent>;
    let service: DecisionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [DecisionComponent],
        providers: []
      })
        .overrideTemplate(DecisionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DecisionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DecisionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Decision('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.decisions[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
