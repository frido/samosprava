import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SamospravaSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { ResolutionCouncilComponent } from 'app/entities/resolution/resolution-council.component';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [SamospravaSharedCommonModule, RouterModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective, ResolutionCouncilComponent],
  entryComponents: [JhiLoginModalComponent],
  exports: [SamospravaSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, ResolutionCouncilComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SamospravaSharedModule {
  static forRoot() {
    return {
      ngModule: SamospravaSharedModule
    };
  }
}
