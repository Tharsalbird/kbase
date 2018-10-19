import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KbaseSharedModule } from 'app/shared';
import {
    RegistroComponent,
    RegistroDetailComponent,
    RegistroUpdateComponent,
    RegistroDeletePopupComponent,
    RegistroDeleteDialogComponent,
    registroRoute,
    registroPopupRoute
} from './';

const ENTITY_STATES = [...registroRoute, ...registroPopupRoute];

@NgModule({
    imports: [KbaseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RegistroComponent,
        RegistroDetailComponent,
        RegistroUpdateComponent,
        RegistroDeleteDialogComponent,
        RegistroDeletePopupComponent
    ],
    entryComponents: [RegistroComponent, RegistroUpdateComponent, RegistroDeleteDialogComponent, RegistroDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KbaseRegistroModule {}
