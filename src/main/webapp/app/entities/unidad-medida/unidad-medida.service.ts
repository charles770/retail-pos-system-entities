import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';

type EntityResponseType = HttpResponse<IUnidadMedida>;
type EntityArrayResponseType = HttpResponse<IUnidadMedida[]>;

@Injectable({ providedIn: 'root' })
export class UnidadMedidaService {
  public resourceUrl = SERVER_API_URL + 'api/unidad-medidas';

  constructor(protected http: HttpClient) {}

  create(unidadMedida: IUnidadMedida): Observable<EntityResponseType> {
    return this.http.post<IUnidadMedida>(this.resourceUrl, unidadMedida, { observe: 'response' });
  }

  update(unidadMedida: IUnidadMedida): Observable<EntityResponseType> {
    return this.http.put<IUnidadMedida>(this.resourceUrl, unidadMedida, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUnidadMedida>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUnidadMedida[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
