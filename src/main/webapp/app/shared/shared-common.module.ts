import {NgModule} from '@angular/core';

import {JhiAlertComponent, JhiAlertErrorComponent, KbaseSharedLibsModule} from './';

@NgModule({
    imports: [KbaseSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [KbaseSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class KbaseSharedCommonModule {
}
