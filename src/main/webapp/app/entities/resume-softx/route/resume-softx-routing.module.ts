import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResumeSoftxComponent } from '../list/resume-softx.component';
import { ResumeSoftxDetailComponent } from '../detail/resume-softx-detail.component';
import { ResumeSoftxUpdateComponent } from '../update/resume-softx-update.component';
import { ResumeSoftxRoutingResolveService } from './resume-softx-routing-resolve.service';

const resumeSoftxRoute: Routes = [
  {
    path: '',
    component: ResumeSoftxComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResumeSoftxDetailComponent,
    resolve: {
      resumeSoftx: ResumeSoftxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResumeSoftxUpdateComponent,
    resolve: {
      resumeSoftx: ResumeSoftxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResumeSoftxUpdateComponent,
    resolve: {
      resumeSoftx: ResumeSoftxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resumeSoftxRoute)],
  exports: [RouterModule],
})
export class ResumeSoftxRoutingModule {}
