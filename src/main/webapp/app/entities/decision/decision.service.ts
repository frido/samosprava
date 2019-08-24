import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDecision } from 'app/shared/model/decision.model';

type EntityResponseType = HttpResponse<IDecision>;
type EntityArrayResponseType = HttpResponse<IDecision[]>;

@Injectable({ providedIn: 'root' })
export class DecisionService {
  public resourceUrl = SERVER_API_URL + 'api/decisions';

  constructor(protected http: HttpClient) {}

  create(decision: IDecision): Observable<EntityResponseType> {
    return this.http.post<IDecision>(this.resourceUrl, decision, { observe: 'response' });
  }

  update(decision: IDecision): Observable<EntityResponseType> {
    return this.http.put<IDecision>(this.resourceUrl, decision, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IDecision>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDecision[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
