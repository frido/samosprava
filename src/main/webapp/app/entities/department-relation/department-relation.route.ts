import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DepartmentRelation } from 'app/shared/model/department-relation.model';
import { DepartmentRelationService } from './department-relation.service';
import { DepartmentRelationComponent } from './department-relation.component';
import { DepartmentRelationDetailComponent } from './department-relation-detail.component';
import { DepartmentRelationUpdateComponent } from './department-relation-update.component';
import { DepartmentRelationDeletePopupComponent } from './department-relation-delete-dialog.component';
import { IDepartmentRelation } from 'app/shared/model/department-relation.model';

@Injectable({ providedIn: 'root' })
export class DepartmentRelationResolve implements Resolve<IDepartmentRelation> {
  constructor(private service: DepartmentRelationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDepartmentRelation> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DepartmentRelation>) => response.ok),
        map((departmentRelation: HttpResponse<DepartmentRelation>) => departmentRelation.body)
      );
    }
    return of(new DepartmentRelation());
  }
}

export const departmentRelationRoute: Routes = [
  {
    path: '',
    component: DepartmentRelationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DepartmentRelations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DepartmentRelationDetailComponent,
    resolve: {
      departmentRelation: DepartmentRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DepartmentRelations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DepartmentRelationUpdateComponent,
    resolve: {
      departmentRelation: DepartmentRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DepartmentRelations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DepartmentRelationUpdateComponent,
    resolve: {
      departmentRelation: DepartmentRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DepartmentRelations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const departmentRelationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DepartmentRelationDeletePopupComponent,
    resolve: {
      departmentRelation: DepartmentRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DepartmentRelations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
