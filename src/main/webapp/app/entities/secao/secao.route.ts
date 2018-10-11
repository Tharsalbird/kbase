import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Secao } from 'app/shared/model/secao.model';
import { SecaoService } from './secao.service';
import { SecaoComponent } from './secao.component';
import { SecaoDetailComponent } from './secao-detail.component';
import { SecaoUpdateComponent } from './secao-update.component';
import { SecaoDeletePopupComponent } from './secao-delete-dialog.component';
import { ISecao } from 'app/shared/model/secao.model';

@Injectable({ providedIn: 'root' })
export class SecaoResolve implements Resolve<ISecao> {
    constructor(private service: SecaoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((secao: HttpResponse<Secao>) => secao.body));
        }
        return of(new Secao());
    }
}

export const secaoRoute: Routes = [
    {
        path: 'secao',
        component: SecaoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Seções'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'secao/:id/view',
        component: SecaoDetailComponent,
        resolve: {
            secao: SecaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Seções'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'secao/new',
        component: SecaoUpdateComponent,
        resolve: {
            secao: SecaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Seções'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'secao/:id/edit',
        component: SecaoUpdateComponent,
        resolve: {
            secao: SecaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Seções'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const secaoPopupRoute: Routes = [
    {
        path: 'secao/:id/delete',
        component: SecaoDeletePopupComponent,
        resolve: {
            secao: SecaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Seções'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
