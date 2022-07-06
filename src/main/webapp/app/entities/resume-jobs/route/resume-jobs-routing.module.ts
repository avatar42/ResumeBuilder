import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResumeJobsComponent } from '../list/resume-jobs.component';
import { ResumeJobsDetailComponent } from '../detail/resume-jobs-detail.component';
import { ResumeJobsUpdateComponent } from '../update/resume-jobs-update.component';
import { ResumeJobsRoutingResolveService } from './resume-jobs-routing-resolve.service';

const resumeJobsRoute: Routes = [
  {
    path: '',
    component: ResumeJobsComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResumeJobsDetailComponent,
    resolve: {
      resumeJobs: ResumeJobsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResumeJobsUpdateComponent,
    resolve: {
      resumeJobs: ResumeJobsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResumeJobsUpdateComponent,
    resolve: {
      resumeJobs: ResumeJobsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resumeJobsRoute)],
  exports: [RouterModule],
})
export class ResumeJobsRoutingModule {}
