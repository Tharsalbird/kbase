import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IChaveErro } from 'app/shared/model/chave-erro.model';

@Component({
    selector: 'jhi-chave-erro-detail',
    templateUrl: './chave-erro-detail.component.html'
})
export class ChaveErroDetailComponent implements OnInit {
    chaveErro: IChaveErro;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}
