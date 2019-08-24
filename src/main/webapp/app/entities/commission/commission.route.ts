import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Commission } from 'app/shared/model/commission.model';
import { CommissionService } from './commission.service';
import { CommissionComponent } from './commission.component';
import { CommissionDetailComponent } from './commission-detail.component';
import { CommissionUpdateComponent } from './commission-update.component';
import { CommissionDeletePopupComponent } from './commission-delete-dialog.component';
import { ICommission } from 'app/shared/model/commission.model';

@Injectable({ providedIn: 'root' })
export class CommissionResolve implements Resolve<ICommission> {
  constructor(private service: CommissionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommission> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Commission>) => response.ok),
        map((commission: HttpResponse<Commission>) => commission.body)
      );
    }
    return of(new Commission());
  }
}

export const commissionRoute: Routes = [
  {
    path: '',
    component: CommissionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Commissions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CommissionDetailComponent,
    resolve: {
      commission: CommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Commissions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CommissionUpdateComponent,
    resolve: {
      commission: CommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Commissions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CommissionUpdateComponent,
    resolve: {
      commission: CommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Commissions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const commissionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CommissionDeletePopupComponent,
    resolve: {
      commission: CommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Commissions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
