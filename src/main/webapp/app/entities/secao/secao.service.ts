import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISecao } from 'app/shared/model/secao.model';

type EntityResponseType = HttpResponse<ISecao>;
type EntityArrayResponseType = HttpResponse<ISecao[]>;

@Injectable({ providedIn: 'root' })
export class SecaoService {
    private resourceUrl = SERVER_API_URL + 'api/secaos';

    constructor(private http: HttpClient) {}

    create(secao: ISecao): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(secao);
        return this.http
            .post<ISecao>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(secao: ISecao): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(secao);
        return this.http
            .put<ISecao>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISecao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISecao[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(secao: ISecao): ISecao {
        const copy: ISecao = Object.assign({}, secao, {
            dataCriacao: secao.dataCriacao != null && secao.dataCriacao.isValid() ? secao.dataCriacao.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dataCriacao = res.body.dataCriacao != null ? moment(res.body.dataCriacao) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((secao: ISecao) => {
            secao.dataCriacao = secao.dataCriacao != null ? moment(secao.dataCriacao) : null;
        });
        return res;
    }
}
