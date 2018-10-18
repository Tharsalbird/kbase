import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {KbaseSharedModule} from 'app/shared';
import {
    SecaoComponent,
    SecaoDeleteDialogComponent,
    SecaoDeletePopupComponent,
    SecaoDetailComponent,
    SecaoFilterComponent,
    SecaoFilterFormComponent,
    secaoPopupRoute,
    secaoRoute,
    SecaoUpdateComponent
} from './';

const ENTITY_STATES = [...secaoRoute, ...secaoPopupRoute];

@NgModule({
    imports: [KbaseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SecaoComponent, SecaoDetailComponent, SecaoUpdateComponent, SecaoDeleteDialogComponent, SecaoDeletePopupComponent,
        SecaoFilterComponent, SecaoFilterFormComponent],
    entryComponents: [SecaoComponent, SecaoUpdateComponent, SecaoDeleteDialogComponent, SecaoDeletePopupComponent,
        SecaoFilterComponent, SecaoFilterFormComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KbaseSecaoModule {
}
