import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Council } from 'app/shared/model/council.model';
import { CouncilService } from './council.service';
import { CouncilComponent } from './council.component';
import { CouncilDetailComponent } from './council-detail.component';
import { CouncilUpdateComponent } from './council-update.component';
import { CouncilDeletePopupComponent } from './council-delete-dialog.component';
import { ICouncil } from 'app/shared/model/council.model';
import { ResolutionCouncilComponent } from '../resolution/resolution-council.component';

@Injectable({ providedIn: 'root' })
export class CouncilResolve implements Resolve<ICouncil> {
  constructor(private service: CouncilService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICouncil> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Council>) => response.ok),
        map((council: HttpResponse<Council>) => council.body)
      );
    }
    return of(new Council());
  }
}

export const councilRoute: Routes = [
  {
    path: '',
    component: CouncilComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Councils'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CouncilDetailComponent,
    resolve: {
      council: CouncilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Councils'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CouncilUpdateComponent,
    resolve: {
      council: CouncilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Councils'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CouncilUpdateComponent,
    resolve: {
      council: CouncilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Councils'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/resolutions',
    component: ResolutionCouncilComponent,
    resolve: {
      council: CouncilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Councils'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const councilPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CouncilDeletePopupComponent,
    resolve: {
      council: CouncilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Councils'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
