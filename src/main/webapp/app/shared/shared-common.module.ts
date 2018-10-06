import { NgModule } from '@angular/core';

import { KbaseSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [KbaseSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [KbaseSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class KbaseSharedCommonModule {}
