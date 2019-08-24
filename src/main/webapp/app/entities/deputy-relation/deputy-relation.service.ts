import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDeputyRelation } from 'app/shared/model/deputy-relation.model';

type EntityResponseType = HttpResponse<IDeputyRelation>;
type EntityArrayResponseType = HttpResponse<IDeputyRelation[]>;

@Injectable({ providedIn: 'root' })
export class DeputyRelationService {
  public resourceUrl = SERVER_API_URL + 'api/deputy-relations';

  constructor(protected http: HttpClient) {}

  create(deputyRelation: IDeputyRelation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deputyRelation);
    return this.http
      .post<IDeputyRelation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(deputyRelation: IDeputyRelation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deputyRelation);
    return this.http
      .put<IDeputyRelation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IDeputyRelation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDeputyRelation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(deputyRelation: IDeputyRelation): IDeputyRelation {
    const copy: IDeputyRelation = Object.assign({}, deputyRelation, {
      from: deputyRelation.from != null && deputyRelation.from.isValid() ? deputyRelation.from.format(DATE_FORMAT) : null,
      to: deputyRelation.to != null && deputyRelation.to.isValid() ? deputyRelation.to.format(DATE_FORMAT) : null
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
      res.body.forEach((deputyRelation: IDeputyRelation) => {
        deputyRelation.from = deputyRelation.from != null ? moment(deputyRelation.from) : null;
        deputyRelation.to = deputyRelation.to != null ? moment(deputyRelation.to) : null;
      });
    }
    return res;
  }
}
