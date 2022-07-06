import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResumeResponComponent } from '../list/resume-respon.component';
import { ResumeResponDetailComponent } from '../detail/resume-respon-detail.component';
import { ResumeResponUpdateComponent } from '../update/resume-respon-update.component';
import { ResumeResponRoutingResolveService } from './resume-respon-routing-resolve.service';

const resumeResponRoute: Routes = [
  {
    path: '',
    component: ResumeResponComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResumeResponDetailComponent,
    resolve: {
      resumeRespon: ResumeResponRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResumeResponUpdateComponent,
    resolve: {
      resumeRespon: ResumeResponRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResumeResponUpdateComponent,
    resolve: {
      resumeRespon: ResumeResponRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resumeResponRoute)],
  exports: [RouterModule],
})
export class ResumeResponRoutingModule {}
