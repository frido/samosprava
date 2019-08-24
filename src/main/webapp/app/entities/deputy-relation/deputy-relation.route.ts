import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DeputyRelation } from 'app/shared/model/deputy-relation.model';
import { DeputyRelationService } from './deputy-relation.service';
import { DeputyRelationComponent } from './deputy-relation.component';
import { DeputyRelationDetailComponent } from './deputy-relation-detail.component';
import { DeputyRelationUpdateComponent } from './deputy-relation-update.component';
import { DeputyRelationDeletePopupComponent } from './deputy-relation-delete-dialog.component';
import { IDeputyRelation } from 'app/shared/model/deputy-relation.model';

@Injectable({ providedIn: 'root' })
export class DeputyRelationResolve implements Resolve<IDeputyRelation> {
  constructor(private service: DeputyRelationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDeputyRelation> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DeputyRelation>) => response.ok),
        map((deputyRelation: HttpResponse<DeputyRelation>) => deputyRelation.body)
      );
    }
    return of(new DeputyRelation());
  }
}

export const deputyRelationRoute: Routes = [
  {
    path: '',
    component: DeputyRelationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DeputyRelations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DeputyRelationDetailComponent,
    resolve: {
      deputyRelation: DeputyRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DeputyRelations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DeputyRelationUpdateComponent,
    resolve: {
      deputyRelation: DeputyRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DeputyRelations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DeputyRelationUpdateComponent,
    resolve: {
      deputyRelation: DeputyRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DeputyRelations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const deputyRelationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DeputyRelationDeletePopupComponent,
    resolve: {
      deputyRelation: DeputyRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DeputyRelations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
