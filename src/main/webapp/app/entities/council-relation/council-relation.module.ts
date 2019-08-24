import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SamospravaSharedModule } from 'app/shared';
import {
  CouncilRelationComponent,
  CouncilRelationDetailComponent,
  CouncilRelationUpdateComponent,
  CouncilRelationDeletePopupComponent,
  CouncilRelationDeleteDialogComponent,
  councilRelationRoute,
  councilRelationPopupRoute
} from './';

const ENTITY_STATES = [...councilRelationRoute, ...councilRelationPopupRoute];

@NgModule({
  imports: [SamospravaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CouncilRelationComponent,
    CouncilRelationDetailComponent,
    CouncilRelationUpdateComponent,
    CouncilRelationDeleteDialogComponent,
    CouncilRelationDeletePopupComponent
  ],
  entryComponents: [
    CouncilRelationComponent,
    CouncilRelationUpdateComponent,
    CouncilRelationDeleteDialogComponent,
    CouncilRelationDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SamospravaCouncilRelationModule {}
