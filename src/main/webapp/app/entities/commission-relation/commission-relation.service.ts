import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommissionRelation } from 'app/shared/model/commission-relation.model';

type EntityResponseType = HttpResponse<ICommissionRelation>;
type EntityArrayResponseType = HttpResponse<ICommissionRelation[]>;

@Injectable({ providedIn: 'root' })
export class CommissionRelationService {
  public resourceUrl = SERVER_API_URL + 'api/commission-relations';

  constructor(protected http: HttpClient) {}

  create(commissionRelation: ICommissionRelation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commissionRelation);
    return this.http
      .post<ICommissionRelation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commissionRelation: ICommissionRelation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commissionRelation);
    return this.http
      .put<ICommissionRelation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ICommissionRelation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommissionRelation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(commissionRelation: ICommissionRelation): ICommissionRelation {
    const copy: ICommissionRelation = Object.assign({}, commissionRelation, {
      from: commissionRelation.from != null && commissionRelation.from.isValid() ? commissionRelation.from.format(DATE_FORMAT) : null,
      to: commissionRelation.to != null && commissionRelation.to.isValid() ? commissionRelation.to.format(DATE_FORMAT) : null
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
      res.body.forEach((commissionRelation: ICommissionRelation) => {
        commissionRelation.from = commissionRelation.from != null ? moment(commissionRelation.from) : null;
        commissionRelation.to = commissionRelation.to != null ? moment(commissionRelation.to) : null;
      });
    }
    return res;
  }
}
