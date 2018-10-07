import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {NgbDateAdapter} from '@ng-bootstrap/ng-bootstrap';

import {NgbDateMomentAdapter} from './util/datepicker-adapter';
import {HasAnyAuthorityDirective, JhiLoginModalComponent, KbaseSharedCommonModule, KbaseSharedLibsModule} from './';

@NgModule({
    imports: [KbaseSharedLibsModule, KbaseSharedCommonModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{provide: NgbDateAdapter, useClass: NgbDateMomentAdapter}],
    entryComponents: [JhiLoginModalComponent],
    exports: [KbaseSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KbaseSharedModule {
}
