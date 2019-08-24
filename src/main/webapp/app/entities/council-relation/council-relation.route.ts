import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CouncilRelation } from 'app/shared/model/council-relation.model';
import { CouncilRelationService } from './council-relation.service';
import { CouncilRelationComponent } from './council-relation.component';
import { CouncilRelationDetailComponent } from './council-relation-detail.component';
import { CouncilRelationUpdateComponent } from './council-relation-update.component';
import { CouncilRelationDeletePopupComponent } from './council-relation-delete-dialog.component';
import { ICouncilRelation } from 'app/shared/model/council-relation.model';

@Injectable({ providedIn: 'root' })
export class CouncilRelationResolve implements Resolve<ICouncilRelation> {
  constructor(private service: CouncilRelationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICouncilRelation> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CouncilRelation>) => response.ok),
        map((councilRelation: HttpResponse<CouncilRelation>) => councilRelation.body)
      );
    }
    return of(new CouncilRelation());
  }
}

export const councilRelationRoute: Routes = [
  {
    path: '',
    component: CouncilRelationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CouncilRelations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CouncilRelationDetailComponent,
    resolve: {
      councilRelation: CouncilRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CouncilRelations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CouncilRelationUpdateComponent,
    resolve: {
      councilRelation: CouncilRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CouncilRelations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CouncilRelationUpdateComponent,
    resolve: {
      councilRelation: CouncilRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CouncilRelations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const councilRelationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CouncilRelationDeletePopupComponent,
    resolve: {
      councilRelation: CouncilRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CouncilRelations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
