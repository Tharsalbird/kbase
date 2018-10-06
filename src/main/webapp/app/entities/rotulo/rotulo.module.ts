import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KbaseSharedModule } from 'app/shared';
import {
    RotuloComponent,
    RotuloDetailComponent,
    RotuloUpdateComponent,
    RotuloDeletePopupComponent,
    RotuloDeleteDialogComponent,
    rotuloRoute,
    rotuloPopupRoute
} from './';

const ENTITY_STATES = [...rotuloRoute, ...rotuloPopupRoute];

@NgModule({
    imports: [KbaseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RotuloComponent, RotuloDetailComponent, RotuloUpdateComponent, RotuloDeleteDialogComponent, RotuloDeletePopupComponent],
    entryComponents: [RotuloComponent, RotuloUpdateComponent, RotuloDeleteDialogComponent, RotuloDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KbaseRotuloModule {}
