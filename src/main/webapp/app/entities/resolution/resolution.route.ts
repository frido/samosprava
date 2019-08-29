import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Resolution } from 'app/shared/model/resolution.model';
import { ResolutionService } from './resolution.service';
import { ResolutionComponent } from './resolution.component';
import { ResolutionDetailComponent } from './resolution-detail.component';
import { ResolutionUpdateComponent } from './resolution-update.component';
import { ResolutionDeletePopupComponent } from './resolution-delete-dialog.component';
import { IResolution } from 'app/shared/model/resolution.model';
import { ResolutionCouncilComponent } from './resolution-council.component';

@Injectable({ providedIn: 'root' })
export class ResolutionResolve implements Resolve<IResolution> {
  constructor(private service: ResolutionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IResolution> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Resolution>) => response.ok),
        map((resolution: HttpResponse<Resolution>) => resolution.body)
      );
    }
    return of(new Resolution());
  }
}

export const resolutionRoute: Routes = [
  {
    path: '',
    component: ResolutionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Resolutions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ResolutionDetailComponent,
    resolve: {
      resolution: ResolutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Resolutions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ResolutionUpdateComponent,
    resolve: {
      resolution: ResolutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Resolutions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ResolutionUpdateComponent,
    resolve: {
      resolution: ResolutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Resolutions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const resolutionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ResolutionDeletePopupComponent,
    resolve: {
      resolution: ResolutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Resolutions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
