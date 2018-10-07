import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {JhiDataUtils} from 'ng-jhipster';

import {IGlossario} from 'app/shared/model/glossario.model';
import {GlossarioService} from './glossario.service';

@Component({
    selector: 'jhi-glossario-update',
    templateUrl: './glossario-update.component.html'
})
export class GlossarioUpdateComponent implements OnInit {
    private _glossario: IGlossario;
    isSaving: boolean;

    constructor(private dataUtils: JhiDataUtils, private glossarioService: GlossarioService, private activatedRoute: ActivatedRoute) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({glossario}) => {
            this.glossario = glossario;
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
        if (this.glossario.id !== undefined) {
            this.subscribeToSaveResponse(this.glossarioService.update(this.glossario));
        } else {
            this.subscribeToSaveResponse(this.glossarioService.create(this.glossario));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGlossario>>) {
        result.subscribe((res: HttpResponse<IGlossario>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    get glossario() {
        return this._glossario;
    }

    set glossario(glossario: IGlossario) {
        this._glossario = glossario;
    }
}
