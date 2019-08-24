import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResolution } from 'app/shared/model/resolution.model';

type EntityResponseType = HttpResponse<IResolution>;
type EntityArrayResponseType = HttpResponse<IResolution[]>;

@Injectable({ providedIn: 'root' })
export class ResolutionService {
  public resourceUrl = SERVER_API_URL + 'api/resolutions';

  constructor(protected http: HttpClient) {}

  create(resolution: IResolution): Observable<EntityResponseType> {
    return this.http.post<IResolution>(this.resourceUrl, resolution, { observe: 'response' });
  }

  update(resolution: IResolution): Observable<EntityResponseType> {
    return this.http.put<IResolution>(this.resourceUrl, resolution, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IResolution>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResolution[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
