import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IElection } from 'app/shared/model/election.model';

type EntityResponseType = HttpResponse<IElection>;
type EntityArrayResponseType = HttpResponse<IElection[]>;

@Injectable({ providedIn: 'root' })
export class ElectionService {
  public resourceUrl = SERVER_API_URL + 'api/elections';

  constructor(protected http: HttpClient) {}

  create(election: IElection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(election);
    return this.http
      .post<IElection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(election: IElection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(election);
    return this.http
      .put<IElection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IElection>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IElection[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(election: IElection): IElection {
    const copy: IElection = Object.assign({}, election, {
      date: election.date != null && election.date.isValid() ? election.date.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date != null ? moment(res.body.date) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((election: IElection) => {
        election.date = election.date != null ? moment(election.date) : null;
      });
    }
    return res;
  }
}
