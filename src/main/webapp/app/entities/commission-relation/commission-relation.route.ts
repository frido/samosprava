import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommissionRelation } from 'app/shared/model/commission-relation.model';
import { CommissionRelationService } from './commission-relation.service';
import { CommissionRelationComponent } from './commission-relation.component';
import { CommissionRelationDetailComponent } from './commission-relation-detail.component';
import { CommissionRelationUpdateComponent } from './commission-relation-update.component';
import { CommissionRelationDeletePopupComponent } from './commission-relation-delete-dialog.component';
import { ICommissionRelation } from 'app/shared/model/commission-relation.model';

@Injectable({ providedIn: 'root' })
export class CommissionRelationResolve implements Resolve<ICommissionRelation> {
  constructor(private service: CommissionRelationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommissionRelation> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CommissionRelation>) => response.ok),
        map((commissionRelation: HttpResponse<CommissionRelation>) => commissionRelation.body)
      );
    }
    return of(new CommissionRelation());
  }
}

export const commissionRelationRoute: Routes = [
  {
    path: '',
    component: CommissionRelationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CommissionRelations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CommissionRelationDetailComponent,
    resolve: {
      commissionRelation: CommissionRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CommissionRelations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CommissionRelationUpdateComponent,
    resolve: {
      commissionRelation: CommissionRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CommissionRelations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CommissionRelationUpdateComponent,
    resolve: {
      commissionRelation: CommissionRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CommissionRelations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const commissionRelationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CommissionRelationDeletePopupComponent,
    resolve: {
      commissionRelation: CommissionRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CommissionRelations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
