import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SamospravaSharedModule } from 'app/shared';
import {
  CouncilComponent,
  CouncilDetailComponent,
  CouncilUpdateComponent,
  CouncilDeletePopupComponent,
  CouncilDeleteDialogComponent,
  councilRoute,
  councilPopupRoute
} from './';
import { SamospravaResolutionModule } from '../resolution/resolution.module';
import { CouncilResolutionsComponent } from './council-resolutions.component';
import { CouncilMainComponent } from './council-main.component';
import { CouncilPersonsComponent } from './council-persons.component';

const ENTITY_STATES = [...councilRoute, ...councilPopupRoute];

@NgModule({
  imports: [SamospravaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CouncilComponent,
    CouncilDetailComponent,
    CouncilUpdateComponent,
    CouncilDeleteDialogComponent,
    CouncilDeletePopupComponent,
    CouncilResolutionsComponent,
    CouncilMainComponent,
    CouncilPersonsComponent
  ],
  entryComponents: [CouncilComponent, CouncilUpdateComponent, CouncilDeleteDialogComponent, CouncilDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SamospravaCouncilModule {}
