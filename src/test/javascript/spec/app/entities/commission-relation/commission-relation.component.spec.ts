/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SamospravaTestModule } from '../../../test.module';
import { CommissionRelationComponent } from 'app/entities/commission-relation/commission-relation.component';
import { CommissionRelationService } from 'app/entities/commission-relation/commission-relation.service';
import { CommissionRelation } from 'app/shared/model/commission-relation.model';

describe('Component Tests', () => {
  describe('CommissionRelation Management Component', () => {
    let comp: CommissionRelationComponent;
    let fixture: ComponentFixture<CommissionRelationComponent>;
    let service: CommissionRelationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [CommissionRelationComponent],
        providers: []
      })
        .overrideTemplate(CommissionRelationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommissionRelationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommissionRelationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommissionRelation('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commissionRelations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
