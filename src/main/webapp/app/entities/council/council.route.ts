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
import { ResolutionEmbeddedComponent } from 'app/shared/model/resolution-embedded.component';
import { CouncilResolutionsComponent } from './council-resolutions.component';
import { CouncilMainComponent } from './council-main.component';
import { CouncilPersonsComponent } from './council-persons.component';

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
    path: ':id/main',
    component: CouncilMainComponent,
    resolve: {
      council: CouncilResolve
    },
    data: {
      authorities: [],
      pageTitle: 'Councils'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/resolutions',
    component: CouncilResolutionsComponent,
    resolve: {
      council: CouncilResolve
    },
    data: {
      authorities: [],
      pageTitle: 'Councils'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/persons',
    component: CouncilPersonsComponent,
    resolve: {
      council: CouncilResolve
    },
    data: {
      authorities: [],
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
