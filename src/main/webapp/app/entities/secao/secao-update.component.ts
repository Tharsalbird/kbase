import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISecao } from 'app/shared/model/secao.model';
import { SecaoService } from './secao.service';

@Component({
    selector: 'jhi-secao-update',
    templateUrl: './secao-update.component.html'
})
export class SecaoUpdateComponent implements OnInit {
    private _secao: ISecao;
    isSaving: boolean;
    dataCriacaoDp: any;

    constructor(private secaoService: SecaoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ secao }) => {
            this.secao = secao;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.secao.id !== undefined) {
            this.subscribeToSaveResponse(this.secaoService.update(this.secao));
        } else {
            this.subscribeToSaveResponse(this.secaoService.create(this.secao));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISecao>>) {
        result.subscribe((res: HttpResponse<ISecao>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get secao() {
        return this._secao;
    }

    set secao(secao: ISecao) {
        this._secao = secao;
    }
}
