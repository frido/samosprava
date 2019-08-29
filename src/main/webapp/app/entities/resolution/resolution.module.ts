import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SamospravaSharedModule } from 'app/shared';
import {
  ResolutionComponent,
  ResolutionDetailComponent,
  ResolutionUpdateComponent,
  ResolutionDeletePopupComponent,
  ResolutionDeleteDialogComponent,
  resolutionRoute,
  resolutionPopupRoute
} from './';
import { ResolutionMainComponent } from './resolution-main.component';

const ENTITY_STATES = [...resolutionRoute, ...resolutionPopupRoute];

@NgModule({
  imports: [SamospravaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ResolutionComponent,
    ResolutionDetailComponent,
    ResolutionUpdateComponent,
    ResolutionDeleteDialogComponent,
    ResolutionDeletePopupComponent,
    ResolutionMainComponent
  ],
  entryComponents: [ResolutionComponent, ResolutionUpdateComponent, ResolutionDeleteDialogComponent, ResolutionDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SamospravaResolutionModule {}
