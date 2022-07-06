import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResumeSoftwareComponent } from '../list/resume-software.component';
import { ResumeSoftwareDetailComponent } from '../detail/resume-software-detail.component';
import { ResumeSoftwareUpdateComponent } from '../update/resume-software-update.component';
import { ResumeSoftwareRoutingResolveService } from './resume-software-routing-resolve.service';

const resumeSoftwareRoute: Routes = [
  {
    path: '',
    component: ResumeSoftwareComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResumeSoftwareDetailComponent,
    resolve: {
      resumeSoftware: ResumeSoftwareRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResumeSoftwareUpdateComponent,
    resolve: {
      resumeSoftware: ResumeSoftwareRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResumeSoftwareUpdateComponent,
    resolve: {
      resumeSoftware: ResumeSoftwareRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resumeSoftwareRoute)],
  exports: [RouterModule],
})
export class ResumeSoftwareRoutingModule {}
