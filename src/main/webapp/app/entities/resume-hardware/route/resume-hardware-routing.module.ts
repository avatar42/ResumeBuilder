import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResumeHardwareComponent } from '../list/resume-hardware.component';
import { ResumeHardwareDetailComponent } from '../detail/resume-hardware-detail.component';
import { ResumeHardwareUpdateComponent } from '../update/resume-hardware-update.component';
import { ResumeHardwareRoutingResolveService } from './resume-hardware-routing-resolve.service';

const resumeHardwareRoute: Routes = [
  {
    path: '',
    component: ResumeHardwareComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResumeHardwareDetailComponent,
    resolve: {
      resumeHardware: ResumeHardwareRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResumeHardwareUpdateComponent,
    resolve: {
      resumeHardware: ResumeHardwareRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResumeHardwareUpdateComponent,
    resolve: {
      resumeHardware: ResumeHardwareRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resumeHardwareRoute)],
  exports: [RouterModule],
})
export class ResumeHardwareRoutingModule {}
