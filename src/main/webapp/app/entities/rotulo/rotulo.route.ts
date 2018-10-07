import {Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {UserRouteAccessService} from 'app/core';
import {of} from 'rxjs';
import {map} from 'rxjs/operators';
import {IRotulo, Rotulo} from 'app/shared/model/rotulo.model';
import {RotuloService} from './rotulo.service';
import {RotuloComponent} from './rotulo.component';
import {RotuloDetailComponent} from './rotulo-detail.component';
import {RotuloUpdateComponent} from './rotulo-update.component';
import {RotuloDeletePopupComponent} from './rotulo-delete-dialog.component';

@Injectable({providedIn: 'root'})
export class RotuloResolve implements Resolve<IRotulo> {
    constructor(private service: RotuloService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((rotulo: HttpResponse<Rotulo>) => rotulo.body));
        }
        return of(new Rotulo());
    }
}

export const rotuloRoute: Routes = [
    {
        path: 'rotulo',
        component: RotuloComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rotulos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rotulo/:id/view',
        component: RotuloDetailComponent,
        resolve: {
            rotulo: RotuloResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rotulos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rotulo/new',
        component: RotuloUpdateComponent,
        resolve: {
            rotulo: RotuloResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rotulos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rotulo/:id/edit',
        component: RotuloUpdateComponent,
        resolve: {
            rotulo: RotuloResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rotulos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rotuloPopupRoute: Routes = [
    {
        path: 'rotulo/:id/delete',
        component: RotuloDeletePopupComponent,
        resolve: {
            rotulo: RotuloResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rotulos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
