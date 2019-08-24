/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SamospravaTestModule } from '../../../test.module';
import { DeputyRelationComponent } from 'app/entities/deputy-relation/deputy-relation.component';
import { DeputyRelationService } from 'app/entities/deputy-relation/deputy-relation.service';
import { DeputyRelation } from 'app/shared/model/deputy-relation.model';

describe('Component Tests', () => {
  describe('DeputyRelation Management Component', () => {
    let comp: DeputyRelationComponent;
    let fixture: ComponentFixture<DeputyRelationComponent>;
    let service: DeputyRelationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [DeputyRelationComponent],
        providers: []
      })
        .overrideTemplate(DeputyRelationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeputyRelationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeputyRelationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DeputyRelation('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.deputyRelations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
