import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Election } from 'app/shared/model/election.model';
import { ElectionService } from './election.service';
import { ElectionComponent } from './election.component';
import { ElectionDetailComponent } from './election-detail.component';
import { ElectionUpdateComponent } from './election-update.component';
import { ElectionDeletePopupComponent } from './election-delete-dialog.component';
import { IElection } from 'app/shared/model/election.model';

@Injectable({ providedIn: 'root' })
export class ElectionResolve implements Resolve<IElection> {
  constructor(private service: ElectionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IElection> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Election>) => response.ok),
        map((election: HttpResponse<Election>) => election.body)
      );
    }
    return of(new Election());
  }
}

export const electionRoute: Routes = [
  {
    path: '',
    component: ElectionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Elections'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ElectionDetailComponent,
    resolve: {
      election: ElectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Elections'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ElectionUpdateComponent,
    resolve: {
      election: ElectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Elections'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ElectionUpdateComponent,
    resolve: {
      election: ElectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Elections'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const electionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ElectionDeletePopupComponent,
    resolve: {
      election: ElectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Elections'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
