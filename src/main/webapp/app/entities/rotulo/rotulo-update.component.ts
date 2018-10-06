import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRotulo } from 'app/shared/model/rotulo.model';
import { RotuloService } from './rotulo.service';

@Component({
    selector: 'jhi-rotulo-update',
    templateUrl: './rotulo-update.component.html'
})
export class RotuloUpdateComponent implements OnInit {
    private _rotulo: IRotulo;
    isSaving: boolean;

    constructor(private rotuloService: RotuloService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rotulo }) => {
            this.rotulo = rotulo;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.rotulo.id !== undefined) {
            this.subscribeToSaveResponse(this.rotuloService.update(this.rotulo));
        } else {
            this.subscribeToSaveResponse(this.rotuloService.create(this.rotulo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRotulo>>) {
        result.subscribe((res: HttpResponse<IRotulo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get rotulo() {
        return this._rotulo;
    }

    set rotulo(rotulo: IRotulo) {
        this._rotulo = rotulo;
    }
}
