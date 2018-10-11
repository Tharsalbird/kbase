import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISecao } from 'app/shared/model/secao.model';

@Component({
    selector: 'jhi-secao-detail',
    templateUrl: './secao-detail.component.html'
})
export class SecaoDetailComponent implements OnInit {
    secao: ISecao;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ secao }) => {
            this.secao = secao;
        });
    }

    previousState() {
        window.history.back();
    }
}
