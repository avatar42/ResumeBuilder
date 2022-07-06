import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResumeResponTypeComponent } from '../list/resume-respon-type.component';
import { ResumeResponTypeDetailComponent } from '../detail/resume-respon-type-detail.component';
import { ResumeResponTypeUpdateComponent } from '../update/resume-respon-type-update.component';
import { ResumeResponTypeRoutingResolveService } from './resume-respon-type-routing-resolve.service';

const resumeResponTypeRoute: Routes = [
  {
    path: '',
    component: ResumeResponTypeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResumeResponTypeDetailComponent,
    resolve: {
      resumeResponType: ResumeResponTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResumeResponTypeUpdateComponent,
    resolve: {
      resumeResponType: ResumeResponTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResumeResponTypeUpdateComponent,
    resolve: {
      resumeResponType: ResumeResponTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resumeResponTypeRoute)],
  exports: [RouterModule],
})
export class ResumeResponTypeRoutingModule {}
