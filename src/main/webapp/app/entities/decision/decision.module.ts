import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SamospravaSharedModule } from 'app/shared';
import {
  DecisionComponent,
  DecisionDetailComponent,
  DecisionUpdateComponent,
  DecisionDeletePopupComponent,
  DecisionDeleteDialogComponent,
  decisionRoute,
  decisionPopupRoute
} from './';

const ENTITY_STATES = [...decisionRoute, ...decisionPopupRoute];

@NgModule({
  imports: [SamospravaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DecisionComponent,
    DecisionDetailComponent,
    DecisionUpdateComponent,
    DecisionDeleteDialogComponent,
    DecisionDeletePopupComponent
  ],
  entryComponents: [DecisionComponent, DecisionUpdateComponent, DecisionDeleteDialogComponent, DecisionDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SamospravaDecisionModule {}
