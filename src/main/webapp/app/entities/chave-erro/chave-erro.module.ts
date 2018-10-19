import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KbaseSharedModule } from 'app/shared';
import {
    ChaveErroComponent,
    ChaveErroDetailComponent,
    ChaveErroUpdateComponent,
    ChaveErroDeletePopupComponent,
    ChaveErroDeleteDialogComponent,
    chaveErroRoute,
    chaveErroPopupRoute
} from './';

const ENTITY_STATES = [...chaveErroRoute, ...chaveErroPopupRoute];

@NgModule({
    imports: [KbaseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ChaveErroComponent,
        ChaveErroDetailComponent,
        ChaveErroUpdateComponent,
        ChaveErroDeleteDialogComponent,
        ChaveErroDeletePopupComponent
    ],
    entryComponents: [ChaveErroComponent, ChaveErroUpdateComponent, ChaveErroDeleteDialogComponent, ChaveErroDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KbaseChaveErroModule {}
