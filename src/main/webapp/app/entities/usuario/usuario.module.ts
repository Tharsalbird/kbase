import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {KbaseSharedModule} from 'app/shared';
import {UsuarioComponent, UsuarioDeleteDialogComponent, UsuarioDeletePopupComponent, UsuarioDetailComponent, usuarioPopupRoute, usuarioRoute, UsuarioUpdateComponent} from './';

const ENTITY_STATES = [...usuarioRoute, ...usuarioPopupRoute];

@NgModule({
    imports: [KbaseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UsuarioComponent,
        UsuarioDetailComponent,
        UsuarioUpdateComponent,
        UsuarioDeleteDialogComponent,
        UsuarioDeletePopupComponent
    ],
    entryComponents: [UsuarioComponent, UsuarioUpdateComponent, UsuarioDeleteDialogComponent, UsuarioDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KbaseUsuarioModule {
}
