import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SamospravaSharedModule } from 'app/shared';
import {
  ElectionComponent,
  ElectionDetailComponent,
  ElectionUpdateComponent,
  ElectionDeletePopupComponent,
  ElectionDeleteDialogComponent,
  electionRoute,
  electionPopupRoute
} from './';

const ENTITY_STATES = [...electionRoute, ...electionPopupRoute];

@NgModule({
  imports: [SamospravaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ElectionComponent,
    ElectionDetailComponent,
    ElectionUpdateComponent,
    ElectionDeleteDialogComponent,
    ElectionDeletePopupComponent
  ],
  entryComponents: [ElectionComponent, ElectionUpdateComponent, ElectionDeleteDialogComponent, ElectionDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SamospravaElectionModule {}
