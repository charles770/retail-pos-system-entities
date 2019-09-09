import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IImpuesto } from 'app/shared/model/impuesto.model';

type EntityResponseType = HttpResponse<IImpuesto>;
type EntityArrayResponseType = HttpResponse<IImpuesto[]>;

@Injectable({ providedIn: 'root' })
export class ImpuestoService {
  public resourceUrl = SERVER_API_URL + 'api/impuestos';

  constructor(protected http: HttpClient) {}

  create(impuesto: IImpuesto): Observable<EntityResponseType> {
    return this.http.post<IImpuesto>(this.resourceUrl, impuesto, { observe: 'response' });
  }

  update(impuesto: IImpuesto): Observable<EntityResponseType> {
    return this.http.put<IImpuesto>(this.resourceUrl, impuesto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IImpuesto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IImpuesto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
