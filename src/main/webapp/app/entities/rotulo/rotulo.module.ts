import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {KbaseSharedModule} from 'app/shared';
import {RotuloComponent, RotuloDeleteDialogComponent, RotuloDeletePopupComponent, RotuloDetailComponent, rotuloPopupRoute, rotuloRoute, RotuloUpdateComponent} from './';

const ENTITY_STATES = [...rotuloRoute, ...rotuloPopupRoute];

@NgModule({
    imports: [KbaseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RotuloComponent, RotuloDetailComponent, RotuloUpdateComponent, RotuloDeleteDialogComponent, RotuloDeletePopupComponent],
    entryComponents: [RotuloComponent, RotuloUpdateComponent, RotuloDeleteDialogComponent, RotuloDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KbaseRotuloModule {
}
