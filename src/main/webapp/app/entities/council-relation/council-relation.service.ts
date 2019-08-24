import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICouncilRelation } from 'app/shared/model/council-relation.model';

type EntityResponseType = HttpResponse<ICouncilRelation>;
type EntityArrayResponseType = HttpResponse<ICouncilRelation[]>;

@Injectable({ providedIn: 'root' })
export class CouncilRelationService {
  public resourceUrl = SERVER_API_URL + 'api/council-relations';

  constructor(protected http: HttpClient) {}

  create(councilRelation: ICouncilRelation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(councilRelation);
    return this.http
      .post<ICouncilRelation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(councilRelation: ICouncilRelation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(councilRelation);
    return this.http
      .put<ICouncilRelation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ICouncilRelation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICouncilRelation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(councilRelation: ICouncilRelation): ICouncilRelation {
    const copy: ICouncilRelation = Object.assign({}, councilRelation, {
      from: councilRelation.from != null && councilRelation.from.isValid() ? councilRelation.from.format(DATE_FORMAT) : null,
      to: councilRelation.to != null && councilRelation.to.isValid() ? councilRelation.to.format(DATE_FORMAT) : null
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
      res.body.forEach((councilRelation: ICouncilRelation) => {
        councilRelation.from = councilRelation.from != null ? moment(councilRelation.from) : null;
        councilRelation.to = councilRelation.to != null ? moment(councilRelation.to) : null;
      });
    }
    return res;
  }
}
