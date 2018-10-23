import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Registro } from 'app/shared/model/registro.model';
import { RegistroService } from './registro.service';
import { RegistroComponent } from './registro.component';
import { RegistroDetailComponent } from './registro-detail.component';
import { RegistroUpdateComponent } from './registro-update.component';
import { RegistroDeletePopupComponent } from './registro-delete-dialog.component';
import { IRegistro } from 'app/shared/model/registro.model';

@Injectable({ providedIn: 'root' })
export class RegistroResolve implements Resolve<IRegistro> {
    constructor(private service: RegistroService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((registro: HttpResponse<Registro>) => registro.body));
        }
        return of(new Registro());
    }
}

export const registroRoute: Routes = [
    {
        path: 'registro',
        component: RegistroComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            // authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Registros'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'registro/:id/view',
        component: RegistroDetailComponent,
        resolve: {
            registro: RegistroResolve
        },
        data: {
            // authorities: ['ROLE_USER'],
            pageTitle: 'Registros'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'registro/new',
        component: RegistroUpdateComponent,
        resolve: {
            registro: RegistroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Registros'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'registro/:id/edit',
        component: RegistroUpdateComponent,
        resolve: {
            registro: RegistroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Registros'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const registroPopupRoute: Routes = [
    {
        path: 'registro/:id/delete',
        component: RegistroDeletePopupComponent,
        resolve: {
            registro: RegistroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Registros'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
