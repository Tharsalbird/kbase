import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRotulo } from 'app/shared/model/rotulo.model';

@Component({
    selector: 'jhi-rotulo-detail',
    templateUrl: './rotulo-detail.component.html'
})
export class RotuloDetailComponent implements OnInit {
    rotulo: IRotulo;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rotulo }) => {
            this.rotulo = rotulo;
        });
    }

    previousState() {
        window.history.back();
    }
}
