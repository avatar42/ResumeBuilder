import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResumeJobsComponent } from './list/resume-jobs.component';
import { ResumeJobsDetailComponent } from './detail/resume-jobs-detail.component';
import { ResumeJobsUpdateComponent } from './update/resume-jobs-update.component';
import { ResumeJobsDeleteDialogComponent } from './delete/resume-jobs-delete-dialog.component';
import { ResumeJobsRoutingModule } from './route/resume-jobs-routing.module';

@NgModule({
  imports: [SharedModule, ResumeJobsRoutingModule],
  declarations: [ResumeJobsComponent, ResumeJobsDetailComponent, ResumeJobsUpdateComponent, ResumeJobsDeleteDialogComponent],
  entryComponents: [ResumeJobsDeleteDialogComponent],
})
export class ResumeJobsModule {}
