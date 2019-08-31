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
      },
      {
        path: 'council',
        loadChildren: () => import('./council/council.module').then(m => m.SamospravaCouncilModule)
      },
      {
        path: 'commission',
        loadChildren: () => import('./commission/commission.module').then(m => m.SamospravaCommissionModule)
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.SamospravaDepartmentModule)
      },
      {
        path: 'deputy-relation',
        loadChildren: () => import('./deputy-relation/deputy-relation.module').then(m => m.SamospravaDeputyRelationModule)
      },
      {
        path: 'council-relation',
        loadChildren: () => import('./council-relation/council-relation.module').then(m => m.SamospravaCouncilRelationModule)
      },
      {
        path: 'department-relation',
        loadChildren: () => import('./department-relation/department-relation.module').then(m => m.SamospravaDepartmentRelationModule)
      },
      {
        path: 'commission-relation',
        loadChildren: () => import('./commission-relation/commission-relation.module').then(m => m.SamospravaCommissionRelationModule)
      },
      {
        path: 'election',
        loadChildren: () => import('./election/election.module').then(m => m.SamospravaElectionModule)
      },
      {
        path: 'vote',
        loadChildren: () => import('./vote/vote.module').then(m => m.SamospravaVoteModule)
      },
      {
        path: 'person',
        loadChildren: () => import('./person/person.module').then(m => m.SamospravaPersonModule)
      },
      {
        path: 'budget',
        loadChildren: () => import('./budget/budget.module').then(m => m.SamospravaBudgetModule)
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
