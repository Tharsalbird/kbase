import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChaveErro } from 'app/shared/model/chave-erro.model';

type EntityResponseType = HttpResponse<IChaveErro>;
type EntityArrayResponseType = HttpResponse<IChaveErro[]>;

@Injectable({ providedIn: 'root' })
export class ChaveErroService {
    private resourceUrl = SERVER_API_URL + 'api/chave-erros';

    constructor(private http: HttpClient) {}

    create(chaveErro: IChaveErro): Observable<EntityResponseType> {
        return this.http.post<IChaveErro>(this.resourceUrl, chaveErro, { observe: 'response' });
    }

    update(chaveErro: IChaveErro): Observable<EntityResponseType> {
        return this.http.put<IChaveErro>(this.resourceUrl, chaveErro, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IChaveErro>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IChaveErro[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
