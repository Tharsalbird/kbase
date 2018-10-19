import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRegistro } from 'app/shared/model/registro.model';

type EntityResponseType = HttpResponse<IRegistro>;
type EntityArrayResponseType = HttpResponse<IRegistro[]>;

@Injectable({ providedIn: 'root' })
export class RegistroService {
    private resourceUrl = SERVER_API_URL + 'api/registros';

    constructor(private http: HttpClient) {}

    create(registro: IRegistro): Observable<EntityResponseType> {
        return this.http.post<IRegistro>(this.resourceUrl, registro, { observe: 'response' });
    }

    update(registro: IRegistro): Observable<EntityResponseType> {
        return this.http.put<IRegistro>(this.resourceUrl, registro, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRegistro>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRegistro[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
