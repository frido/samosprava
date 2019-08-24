import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'meeting',
        loadChildren: () => import('./meeting/meeting.module').then(m => m.SamospravaMeetingModule)
      },
      {
        path: 'resolution',
        loadChildren: () => import('./resolution/resolution.module').then(m => m.SamospravaResolutionModule)
      },
      {
        path: 'decision',
        loadChildren: () => import('./decision/decision.module').then(m => m.SamospravaDecisionModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SamospravaEntityModule {}
