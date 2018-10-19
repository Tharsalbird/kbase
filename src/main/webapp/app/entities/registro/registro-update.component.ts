import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IRegistro } from 'app/shared/model/registro.model';
import { RegistroService } from './registro.service';
import { ISecao } from 'app/shared/model/secao.model';
import { SecaoService } from 'app/entities/secao';
import { IRotulo } from 'app/shared/model/rotulo.model';
import { RotuloService } from 'app/entities/rotulo';

@Component({
    selector: 'jhi-registro-update',
    templateUrl: './registro-update.component.html'
})
export class RegistroUpdateComponent implements OnInit {
    private _registro: IRegistro;
    isSaving: boolean;

    secaos: ISecao[];

    rotulos: IRotulo[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private registroService: RegistroService,
        private secaoService: SecaoService,
        private rotuloService: RotuloService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ registro }) => {
            this.registro = registro;
        });
        this.secaoService.query().subscribe(
            (res: HttpResponse<ISecao[]>) => {
                this.secaos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rotuloService.query().subscribe(
            (res: HttpResponse<IRotulo[]>) => {
                this.rotulos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.registro.id !== undefined) {
            this.subscribeToSaveResponse(this.registroService.update(this.registro));
        } else {
            this.subscribeToSaveResponse(this.registroService.create(this.registro));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRegistro>>) {
        result.subscribe((res: HttpResponse<IRegistro>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackSecaoById(index: number, item: ISecao) {
        return item.id;
    }

    trackRotuloById(index: number, item: IRotulo) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get registro() {
        return this._registro;
    }

    set registro(registro: IRegistro) {
        this._registro = registro;
    }
}
