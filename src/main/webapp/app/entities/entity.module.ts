import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { KbaseGlossarioModule } from './glossario/glossario.module';
import { KbaseRotuloModule } from './rotulo/rotulo.module';
import { KbaseUsuarioModule } from './usuario/usuario.module';

import { KbaseSecaoModule } from './secao/secao.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        KbaseGlossarioModule,
        KbaseRotuloModule,
        KbaseUsuarioModule,
        KbaseSecaoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KbaseEntityModule {}
