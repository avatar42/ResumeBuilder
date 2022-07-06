import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResumeSummaryComponent } from './list/resume-summary.component';
import { ResumeSummaryDetailComponent } from './detail/resume-summary-detail.component';
import { ResumeSummaryUpdateComponent } from './update/resume-summary-update.component';
import { ResumeSummaryDeleteDialogComponent } from './delete/resume-summary-delete-dialog.component';
import { ResumeSummaryRoutingModule } from './route/resume-summary-routing.module';

@NgModule({
  imports: [SharedModule, ResumeSummaryRoutingModule],
  declarations: [ResumeSummaryComponent, ResumeSummaryDetailComponent, ResumeSummaryUpdateComponent, ResumeSummaryDeleteDialogComponent],
  entryComponents: [ResumeSummaryDeleteDialogComponent],
})
export class ResumeSummaryModule {}
