import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDepartmentRelation } from 'app/shared/model/department-relation.model';

type EntityResponseType = HttpResponse<IDepartmentRelation>;
type EntityArrayResponseType = HttpResponse<IDepartmentRelation[]>;

@Injectable({ providedIn: 'root' })
export class DepartmentRelationService {
  public resourceUrl = SERVER_API_URL + 'api/department-relations';

  constructor(protected http: HttpClient) {}

  create(departmentRelation: IDepartmentRelation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(departmentRelation);
    return this.http
      .post<IDepartmentRelation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(departmentRelation: IDepartmentRelation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(departmentRelation);
    return this.http
      .put<IDepartmentRelation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IDepartmentRelation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDepartmentRelation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(departmentRelation: IDepartmentRelation): IDepartmentRelation {
    const copy: IDepartmentRelation = Object.assign({}, departmentRelation, {
      from: departmentRelation.from != null && departmentRelation.from.isValid() ? departmentRelation.from.format(DATE_FORMAT) : null,
      to: departmentRelation.to != null && departmentRelation.to.isValid() ? departmentRelation.to.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.from = res.body.from != null ? moment(res.body.from) : null;
      res.body.to = res.body.to != null ? moment(res.body.to) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((departmentRelation: IDepartmentRelation) => {
        departmentRelation.from = departmentRelation.from != null ? moment(departmentRelation.from) : null;
        departmentRelation.to = departmentRelation.to != null ? moment(departmentRelation.to) : null;
      });
    }
    return res;
  }
}
