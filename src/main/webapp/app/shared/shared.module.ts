import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SamospravaSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { ResolutionEmbeddedComponent } from './model/resolution-embedded.component';
import { RouterModule } from '@angular/router';
import { PersonEmbeddedComponent } from './model/person-embedded.component';

@NgModule({
  imports: [SamospravaSharedCommonModule, RouterModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective, ResolutionEmbeddedComponent, PersonEmbeddedComponent],
  entryComponents: [JhiLoginModalComponent],
  exports: [
    SamospravaSharedCommonModule,
    JhiLoginModalComponent,
    HasAnyAuthorityDirective,
    ResolutionEmbeddedComponent,
    PersonEmbeddedComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SamospravaSharedModule {
  static forRoot() {
    return {
      ngModule: SamospravaSharedModule
    };
  }
}
