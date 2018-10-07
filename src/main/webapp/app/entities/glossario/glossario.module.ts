import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {KbaseSharedModule} from 'app/shared';
import {
    GlossarioComponent,
    GlossarioDeleteDialogComponent,
    GlossarioDeletePopupComponent,
    GlossarioDetailComponent,
    glossarioPopupRoute,
    glossarioRoute,
    GlossarioUpdateComponent
} from './';

const ENTITY_STATES = [...glossarioRoute, ...glossarioPopupRoute];

@NgModule({
    imports: [KbaseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GlossarioComponent,
        GlossarioDetailComponent,
        GlossarioUpdateComponent,
        GlossarioDeleteDialogComponent,
        GlossarioDeletePopupComponent
    ],
    entryComponents: [GlossarioComponent, GlossarioUpdateComponent, GlossarioDeleteDialogComponent, GlossarioDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KbaseGlossarioModule {
}
