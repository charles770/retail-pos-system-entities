import { NgModule } from '@angular/core';

import { RetailPosSystemSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [RetailPosSystemSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [RetailPosSystemSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class RetailPosSystemSharedCommonModule {}
