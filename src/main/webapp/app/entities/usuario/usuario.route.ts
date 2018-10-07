import {Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiResolvePagingParams} from 'ng-jhipster';
import {UserRouteAccessService} from 'app/core';
import {of} from 'rxjs';
import {map} from 'rxjs/operators';
import {IUsuario, Usuario} from 'app/shared/model/usuario.model';
import {UsuarioService} from './usuario.service';
import {UsuarioComponent} from './usuario.component';
import {UsuarioDetailComponent} from './usuario-detail.component';
import {UsuarioUpdateComponent} from './usuario-update.component';
import {UsuarioDeletePopupComponent} from './usuario-delete-dialog.component';

@Injectable({providedIn: 'root'})
export class UsuarioResolve implements Resolve<IUsuario> {
    constructor(private service: UsuarioService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((usuario: HttpResponse<Usuario>) => usuario.body));
        }
        return of(new Usuario());
    }
}

export const usuarioRoute: Routes = [
    {
        path: 'usuario',
        component: UsuarioComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Usuarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'usuario/:id/view',
        component: UsuarioDetailComponent,
        resolve: {
            usuario: UsuarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Usuarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'usuario/new',
        component: UsuarioUpdateComponent,
        resolve: {
            usuario: UsuarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Usuarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'usuario/:id/edit',
        component: UsuarioUpdateComponent,
        resolve: {
            usuario: UsuarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Usuarios'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const usuarioPopupRoute: Routes = [
    {
        path: 'usuario/:id/delete',
        component: UsuarioDeletePopupComponent,
        resolve: {
            usuario: UsuarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Usuarios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
