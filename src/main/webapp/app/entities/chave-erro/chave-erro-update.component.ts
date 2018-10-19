import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IChaveErro } from 'app/shared/model/chave-erro.model';
import { ChaveErroService } from './chave-erro.service';

@Component({
    selector: 'jhi-chave-erro-update',
    templateUrl: './chave-erro-update.component.html'
})
export class ChaveErroUpdateComponent implements OnInit {
    private _chaveErro: IChaveErro;
    isSaving: boolean;

    constructor(private dataUtils: JhiDataUtils, private chaveErroService: ChaveErroService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ chaveErro }) => {
            this.chaveErro = chaveErro;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.chaveErro.id !== undefined) {
            this.subscribeToSaveResponse(this.chaveErroService.update(this.chaveErro));
        } else {
            this.subscribeToSaveResponse(this.chaveErroService.create(this.chaveErro));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IChaveErro>>) {
        result.subscribe((res: HttpResponse<IChaveErro>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get chaveErro() {
        return this._chaveErro;
    }

    set chaveErro(chaveErro: IChaveErro) {
        this._chaveErro = chaveErro;
    }
}
