import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICouncil } from 'app/shared/model/council.model';

type EntityResponseType = HttpResponse<ICouncil>;
type EntityArrayResponseType = HttpResponse<ICouncil[]>;

@Injectable({ providedIn: 'root' })
export class CouncilService {
  public resourceUrl = SERVER_API_URL + 'api/councils';

  constructor(protected http: HttpClient) {}

  create(council: ICouncil): Observable<EntityResponseType> {
    return this.http.post<ICouncil>(this.resourceUrl, council, { observe: 'response' });
  }

  update(council: ICouncil): Observable<EntityResponseType> {
    return this.http.put<ICouncil>(this.resourceUrl, council, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ICouncil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICouncil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
