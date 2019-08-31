import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SamospravaSharedModule } from 'app/shared';
import {
  BudgetComponent,
  BudgetDetailComponent,
  BudgetUpdateComponent,
  BudgetDeletePopupComponent,
  BudgetDeleteDialogComponent,
  budgetRoute,
  budgetPopupRoute
} from './';
import { BudgetMainComponent } from './budget-main.component';

const ENTITY_STATES = [...budgetRoute, ...budgetPopupRoute];

@NgModule({
  imports: [SamospravaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BudgetComponent,
    BudgetDetailComponent,
    BudgetUpdateComponent,
    BudgetDeleteDialogComponent,
    BudgetDeletePopupComponent,
    BudgetMainComponent
  ],
  entryComponents: [BudgetComponent, BudgetUpdateComponent, BudgetDeleteDialogComponent, BudgetDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SamospravaBudgetModule {}
