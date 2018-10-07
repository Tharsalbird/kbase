import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

import {SERVER_API_URL} from 'app/app.constants';
import {createRequestOption} from 'app/shared';
import {IRotulo} from 'app/shared/model/rotulo.model';

type EntityResponseType = HttpResponse<IRotulo>;
type EntityArrayResponseType = HttpResponse<IRotulo[]>;

@Injectable({providedIn: 'root'})
export class RotuloService {
    private resourceUrl = SERVER_API_URL + 'api/rotulos';

    constructor(private http: HttpClient) {
    }

    create(rotulo: IRotulo): Observable<EntityResponseType> {
        return this.http.post<IRotulo>(this.resourceUrl, rotulo, {observe: 'response'});
    }

    update(rotulo: IRotulo): Observable<EntityResponseType> {
        return this.http.put<IRotulo>(this.resourceUrl, rotulo, {observe: 'response'});
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRotulo>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRotulo[]>(this.resourceUrl, {params: options, observe: 'response'});
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }
}
