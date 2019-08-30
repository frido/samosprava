import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SamospravaSharedModule } from 'app/shared';
import {
  PersonComponent,
  PersonDetailComponent,
  PersonUpdateComponent,
  PersonDeletePopupComponent,
  PersonDeleteDialogComponent,
  personRoute,
  personPopupRoute
} from './';
import { PersonMainComponent } from './person-main.component';

const ENTITY_STATES = [...personRoute, ...personPopupRoute];

@NgModule({
  imports: [SamospravaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PersonComponent,
    PersonDetailComponent,
    PersonUpdateComponent,
    PersonDeleteDialogComponent,
    PersonDeletePopupComponent,
    PersonMainComponent
  ],
  entryComponents: [PersonComponent, PersonUpdateComponent, PersonDeleteDialogComponent, PersonDeletePopupComponent, PersonMainComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SamospravaPersonModule {}
