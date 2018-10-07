import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {NgbActiveModal, NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {IRotulo} from 'app/shared/model/rotulo.model';
import {RotuloService} from './rotulo.service';

@Component({
    selector: 'jhi-rotulo-delete-dialog',
    templateUrl: './rotulo-delete-dialog.component.html'
})
export class RotuloDeleteDialogComponent {
    rotulo: IRotulo;

    constructor(private rotuloService: RotuloService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rotuloService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rotuloListModification',
                content: 'Deleted an rotulo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rotulo-delete-popup',
    template: ''
})
export class RotuloDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({rotulo}) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RotuloDeleteDialogComponent as Component, {size: 'lg', backdrop: 'static'});
                this.ngbModalRef.componentInstance.rotulo = rotulo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true, queryParamsHandling: 'merge'});
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true, queryParamsHandling: 'merge'});
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
