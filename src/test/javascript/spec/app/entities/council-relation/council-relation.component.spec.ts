/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SamospravaTestModule } from '../../../test.module';
import { CouncilRelationComponent } from 'app/entities/council-relation/council-relation.component';
import { CouncilRelationService } from 'app/entities/council-relation/council-relation.service';
import { CouncilRelation } from 'app/shared/model/council-relation.model';

describe('Component Tests', () => {
  describe('CouncilRelation Management Component', () => {
    let comp: CouncilRelationComponent;
    let fixture: ComponentFixture<CouncilRelationComponent>;
    let service: CouncilRelationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [CouncilRelationComponent],
        providers: []
      })
        .overrideTemplate(CouncilRelationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CouncilRelationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CouncilRelationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CouncilRelation('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.councilRelations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
