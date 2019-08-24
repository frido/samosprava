import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SamospravaSharedModule } from 'app/shared';
import {
  DeputyRelationComponent,
  DeputyRelationDetailComponent,
  DeputyRelationUpdateComponent,
  DeputyRelationDeletePopupComponent,
  DeputyRelationDeleteDialogComponent,
  deputyRelationRoute,
  deputyRelationPopupRoute
} from './';

const ENTITY_STATES = [...deputyRelationRoute, ...deputyRelationPopupRoute];

@NgModule({
  imports: [SamospravaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DeputyRelationComponent,
    DeputyRelationDetailComponent,
    DeputyRelationUpdateComponent,
    DeputyRelationDeleteDialogComponent,
    DeputyRelationDeletePopupComponent
  ],
  entryComponents: [
    DeputyRelationComponent,
    DeputyRelationUpdateComponent,
    DeputyRelationDeleteDialogComponent,
    DeputyRelationDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SamospravaDeputyRelationModule {}
