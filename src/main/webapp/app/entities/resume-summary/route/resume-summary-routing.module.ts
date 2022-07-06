import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResumeSummaryComponent } from '../list/resume-summary.component';
import { ResumeSummaryDetailComponent } from '../detail/resume-summary-detail.component';
import { ResumeSummaryUpdateComponent } from '../update/resume-summary-update.component';
import { ResumeSummaryRoutingResolveService } from './resume-summary-routing-resolve.service';

const resumeSummaryRoute: Routes = [
  {
    path: '',
    component: ResumeSummaryComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResumeSummaryDetailComponent,
    resolve: {
      resumeSummary: ResumeSummaryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResumeSummaryUpdateComponent,
    resolve: {
      resumeSummary: ResumeSummaryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResumeSummaryUpdateComponent,
    resolve: {
      resumeSummary: ResumeSummaryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resumeSummaryRoute)],
  exports: [RouterModule],
})
export class ResumeSummaryRoutingModule {}
