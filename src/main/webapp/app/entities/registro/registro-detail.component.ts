import {Component, OnInit} from '@angular/core';
import {JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks} from 'ng-jhipster';

import {IRegistro} from 'app/shared/model/registro.model';
import {ITEMS_PER_PAGE} from 'app/shared';
import {RegistroService} from 'app/entities/registro/registro.service';
import {HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs';
import {Principal} from 'app/core';
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'jhi-registro-detail',
    templateUrl: './registro-detail.component.html',
    styleUrls: ['registro.scss']
})
export class RegistroDetailComponent implements OnInit {
    registro: IRegistro;
    registros: IRegistro[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;

    constructor(
        private activatedRoute: ActivatedRoute,
        private registroService: RegistroService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {
        this.registros = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({registro}) => {
            this.registro = registro;
        });
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRegistros();
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

    loadAll() {
        this.registroService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IRegistro[]>) => this.paginateRegistros(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    reset() {
        this.page = 0;
        this.registros = [];
        this.loadAll();
    }

    registerChangeInRegistros() {
        this.eventSubscriber = this.eventManager.subscribe('registroListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateRegistros(data: IRegistro[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.registros.push(data[i]);
        }
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
