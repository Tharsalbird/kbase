import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGlossario } from 'app/shared/model/glossario.model';

type EntityResponseType = HttpResponse<IGlossario>;
type EntityArrayResponseType = HttpResponse<IGlossario[]>;

@Injectable({ providedIn: 'root' })
export class GlossarioService {
    private resourceUrl = SERVER_API_URL + 'api/glossarios';

    constructor(private http: HttpClient) {}

    create(glossario: IGlossario): Observable<EntityResponseType> {
        return this.http.post<IGlossario>(this.resourceUrl, glossario, { observe: 'response' });
    }

    update(glossario: IGlossario): Observable<EntityResponseType> {
        return this.http.put<IGlossario>(this.resourceUrl, glossario, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGlossario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGlossario[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
