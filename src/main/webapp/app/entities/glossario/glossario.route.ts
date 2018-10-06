import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Glossario } from 'app/shared/model/glossario.model';
import { GlossarioService } from './glossario.service';
import { GlossarioComponent } from './glossario.component';
import { GlossarioDetailComponent } from './glossario-detail.component';
import { GlossarioUpdateComponent } from './glossario-update.component';
import { GlossarioDeletePopupComponent } from './glossario-delete-dialog.component';
import { IGlossario } from 'app/shared/model/glossario.model';

@Injectable({ providedIn: 'root' })
export class GlossarioResolve implements Resolve<IGlossario> {
    constructor(private service: GlossarioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((glossario: HttpResponse<Glossario>) => glossario.body));
        }
        return of(new Glossario());
    }
}

export const glossarioRoute: Routes = [
    {
        path: 'glossario',
        component: GlossarioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Glossarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'glossario/:id/view',
        component: GlossarioDetailComponent,
        resolve: {
            glossario: GlossarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Glossarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'glossario/new',
        component: GlossarioUpdateComponent,
        resolve: {
            glossario: GlossarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Glossarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'glossario/:id/edit',
        component: GlossarioUpdateComponent,
        resolve: {
            glossario: GlossarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Glossarios'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const glossarioPopupRoute: Routes = [
    {
        path: 'glossario/:id/delete',
        component: GlossarioDeletePopupComponent,
        resolve: {
            glossario: GlossarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Glossarios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
