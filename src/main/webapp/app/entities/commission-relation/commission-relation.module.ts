import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SamospravaSharedModule } from 'app/shared';
import {
  CommissionRelationComponent,
  CommissionRelationDetailComponent,
  CommissionRelationUpdateComponent,
  CommissionRelationDeletePopupComponent,
  CommissionRelationDeleteDialogComponent,
  commissionRelationRoute,
  commissionRelationPopupRoute
} from './';

const ENTITY_STATES = [...commissionRelationRoute, ...commissionRelationPopupRoute];

@NgModule({
  imports: [SamospravaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CommissionRelationComponent,
    CommissionRelationDetailComponent,
    CommissionRelationUpdateComponent,
    CommissionRelationDeleteDialogComponent,
    CommissionRelationDeletePopupComponent
  ],
  entryComponents: [
    CommissionRelationComponent,
    CommissionRelationUpdateComponent,
    CommissionRelationDeleteDialogComponent,
    CommissionRelationDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SamospravaCommissionRelationModule {}
