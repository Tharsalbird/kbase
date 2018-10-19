import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRegistro } from 'app/shared/model/registro.model';

@Component({
    selector: 'jhi-registro-detail',
    templateUrl: './registro-detail.component.html'
})
export class RegistroDetailComponent implements OnInit {
    registro: IRegistro;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ registro }) => {
            this.registro = registro;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
