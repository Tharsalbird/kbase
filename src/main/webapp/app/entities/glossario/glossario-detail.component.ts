import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IGlossario } from 'app/shared/model/glossario.model';

@Component({
    selector: 'jhi-glossario-detail',
    templateUrl: './glossario-detail.component.html'
})
export class GlossarioDetailComponent implements OnInit {
    glossario: IGlossario;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ glossario }) => {
            this.glossario = glossario;
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
