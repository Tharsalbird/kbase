import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChaveErro } from 'app/shared/model/chave-erro.model';
import { ChaveErroService } from './chave-erro.service';

@Component({
    selector: 'jhi-chave-erro-delete-dialog',
    templateUrl: './chave-erro-delete-dialog.component.html'
})
export class ChaveErroDeleteDialogComponent {
    chaveErro: IChaveErro;

    constructor(private chaveErroService: ChaveErroService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.chaveErroService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'chaveErroListModification',
                content: 'Deleted an chaveErro'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-chave-erro-delete-popup',
    template: ''
})
export class ChaveErroDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ chaveErro }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ChaveErroDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.chaveErro = chaveErro;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
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
