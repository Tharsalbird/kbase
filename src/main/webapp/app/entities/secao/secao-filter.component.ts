import {Component, OnDestroy, OnInit} from '@angular/core';
import {NgbActiveModal, NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {ActivatedRoute, Router} from '@angular/router';
import {ISecao} from 'app/shared/model/secao.model';
import {SecaoService} from 'app/entities/secao/secao.service';
import {JhiEventManager} from 'ng-jhipster';

@Component({
    selector: 'jhi-secao-filter-form',
    templateUrl: './secao-filter.component.html'
})
export class SecaoFilterFormComponent {
    secao: ISecao;

    constructor(private secaoService: SecaoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete() {
        this.secaoService.delete(1).subscribe(response => {
            this.eventManager.broadcast({
                name: 'secaoListModification',
                content: 'Deleted an secao'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-secao-filter',
    template: ''
})
export class SecaoFilterComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({secao}) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SecaoFilterFormComponent as Component, {size: 'lg', backdrop: 'static'});
                this.ngbModalRef.componentInstance.secao = secao;
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
