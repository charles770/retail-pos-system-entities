import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPerfil } from 'app/shared/model/perfil.model';

type EntityResponseType = HttpResponse<IPerfil>;
type EntityArrayResponseType = HttpResponse<IPerfil[]>;

@Injectable({ providedIn: 'root' })
export class PerfilService {
  public resourceUrl = SERVER_API_URL + 'api/perfils';

  constructor(protected http: HttpClient) {}

  create(perfil: IPerfil): Observable<EntityResponseType> {
    return this.http.post<IPerfil>(this.resourceUrl, perfil, { observe: 'response' });
  }

  update(perfil: IPerfil): Observable<EntityResponseType> {
    return this.http.put<IPerfil>(this.resourceUrl, perfil, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPerfil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPerfil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
