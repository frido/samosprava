import { NgModule } from '@angular/core';

import { SamospravaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [SamospravaSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [SamospravaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SamospravaSharedCommonModule {}
