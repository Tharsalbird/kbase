import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ChaveErro } from 'app/shared/model/chave-erro.model';
import { ChaveErroService } from './chave-erro.service';
import { ChaveErroComponent } from './chave-erro.component';
import { ChaveErroDetailComponent } from './chave-erro-detail.component';
import { ChaveErroUpdateComponent } from './chave-erro-update.component';
import { ChaveErroDeletePopupComponent } from './chave-erro-delete-dialog.component';
import { IChaveErro } from 'app/shared/model/chave-erro.model';

@Injectable({ providedIn: 'root' })
export class ChaveErroResolve implements Resolve<IChaveErro> {
    constructor(private service: ChaveErroService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((chaveErro: HttpResponse<ChaveErro>) => chaveErro.body));
        }
        return of(new ChaveErro());
    }
}

export const chaveErroRoute: Routes = [
    {
        path: 'chave-erro',
        component: ChaveErroComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ChaveErros'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chave-erro/:id/view',
        component: ChaveErroDetailComponent,
        resolve: {
            chaveErro: ChaveErroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChaveErros'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chave-erro/new',
        component: ChaveErroUpdateComponent,
        resolve: {
            chaveErro: ChaveErroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChaveErros'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chave-erro/:id/edit',
        component: ChaveErroUpdateComponent,
        resolve: {
            chaveErro: ChaveErroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChaveErros'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chaveErroPopupRoute: Routes = [
    {
        path: 'chave-erro/:id/delete',
        component: ChaveErroDeletePopupComponent,
        resolve: {
            chaveErro: ChaveErroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChaveErros'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
