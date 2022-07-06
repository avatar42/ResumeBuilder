import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResumeSoftwareComponent } from './list/resume-software.component';
import { ResumeSoftwareDetailComponent } from './detail/resume-software-detail.component';
import { ResumeSoftwareUpdateComponent } from './update/resume-software-update.component';
import { ResumeSoftwareDeleteDialogComponent } from './delete/resume-software-delete-dialog.component';
import { ResumeSoftwareRoutingModule } from './route/resume-software-routing.module';

@NgModule({
  imports: [SharedModule, ResumeSoftwareRoutingModule],
  declarations: [
    ResumeSoftwareComponent,
    ResumeSoftwareDetailComponent,
    ResumeSoftwareUpdateComponent,
    ResumeSoftwareDeleteDialogComponent,
  ],
  entryComponents: [ResumeSoftwareDeleteDialogComponent],
})
export class ResumeSoftwareModule {}
