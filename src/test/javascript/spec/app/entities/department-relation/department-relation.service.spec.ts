/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DepartmentRelationService } from 'app/entities/department-relation/department-relation.service';
import { IDepartmentRelation, DepartmentRelation, DepartmentRelationType } from 'app/shared/model/department-relation.model';

describe('Service Tests', () => {
  describe('DepartmentRelation Service', () => {
    let injector: TestBed;
    let service: DepartmentRelationService;
    let httpMock: HttpTestingController;
    let elemDefault: IDepartmentRelation;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DepartmentRelationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DepartmentRelation('ID', currentDate, currentDate, DepartmentRelationType.SCHOOL_MEMBER);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find('123')
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a DepartmentRelation', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            from: currentDate,
            to: currentDate
          },
          returnedFromService
        );
        service
          .create(new DepartmentRelation(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a DepartmentRelation', async () => {
        const returnedFromService = Object.assign(
          {
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT),
            type: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            from: currentDate,
            to: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of DepartmentRelation', async () => {
        const returnedFromService = Object.assign(
          {
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT),
            type: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            from: currentDate,
            to: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DepartmentRelation', async () => {
        const rxPromise = service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
