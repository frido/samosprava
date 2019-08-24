import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SamospravaSharedModule } from 'app/shared';
import {
  DepartmentRelationComponent,
  DepartmentRelationDetailComponent,
  DepartmentRelationUpdateComponent,
  DepartmentRelationDeletePopupComponent,
  DepartmentRelationDeleteDialogComponent,
  departmentRelationRoute,
  departmentRelationPopupRoute
} from './';

const ENTITY_STATES = [...departmentRelationRoute, ...departmentRelationPopupRoute];

@NgModule({
  imports: [SamospravaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DepartmentRelationComponent,
    DepartmentRelationDetailComponent,
    DepartmentRelationUpdateComponent,
    DepartmentRelationDeleteDialogComponent,
    DepartmentRelationDeletePopupComponent
  ],
  entryComponents: [
    DepartmentRelationComponent,
    DepartmentRelationUpdateComponent,
    DepartmentRelationDeleteDialogComponent,
    DepartmentRelationDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SamospravaDepartmentRelationModule {}
