import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResumeHardxComponent } from '../list/resume-hardx.component';
import { ResumeHardxDetailComponent } from '../detail/resume-hardx-detail.component';
import { ResumeHardxUpdateComponent } from '../update/resume-hardx-update.component';
import { ResumeHardxRoutingResolveService } from './resume-hardx-routing-resolve.service';

const resumeHardxRoute: Routes = [
  {
    path: '',
    component: ResumeHardxComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResumeHardxDetailComponent,
    resolve: {
      resumeHardx: ResumeHardxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResumeHardxUpdateComponent,
    resolve: {
      resumeHardx: ResumeHardxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResumeHardxUpdateComponent,
    resolve: {
      resumeHardx: ResumeHardxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resumeHardxRoute)],
  exports: [RouterModule],
})
export class ResumeHardxRoutingModule {}
