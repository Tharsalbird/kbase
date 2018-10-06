import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { KbaseGlossarioModule } from './glossario/glossario.module';
import { KbaseRotuloModule } from './rotulo/rotulo.module';
import { KbaseUsuarioModule } from './usuario/usuario.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        KbaseGlossarioModule,
        KbaseRotuloModule,
        KbaseUsuarioModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KbaseEntityModule {}
