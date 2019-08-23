import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SamospravaSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [SamospravaSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [SamospravaSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SamospravaSharedModule {
  static forRoot() {
    return {
      ngModule: SamospravaSharedModule
    };
  }
}
