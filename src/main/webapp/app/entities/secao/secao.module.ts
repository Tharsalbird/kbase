import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KbaseSharedModule } from 'app/shared';
import {
    SecaoComponent,
    SecaoDetailComponent,
    SecaoUpdateComponent,
    SecaoDeletePopupComponent,
    SecaoDeleteDialogComponent,
    secaoRoute,
    secaoPopupRoute
} from './';

const ENTITY_STATES = [...secaoRoute, ...secaoPopupRoute];

@NgModule({
    imports: [KbaseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SecaoComponent, SecaoDetailComponent, SecaoUpdateComponent, SecaoDeleteDialogComponent, SecaoDeletePopupComponent],
    entryComponents: [SecaoComponent, SecaoUpdateComponent, SecaoDeleteDialogComponent, SecaoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KbaseSecaoModule {}
