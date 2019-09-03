import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RetailPosSystemSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [RetailPosSystemSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [RetailPosSystemSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RetailPosSystemSharedModule {
  static forRoot() {
    return {
      ngModule: RetailPosSystemSharedModule
    };
  }
}
