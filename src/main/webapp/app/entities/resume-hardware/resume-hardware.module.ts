import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResumeHardwareComponent } from './list/resume-hardware.component';
import { ResumeHardwareDetailComponent } from './detail/resume-hardware-detail.component';
import { ResumeHardwareUpdateComponent } from './update/resume-hardware-update.component';
import { ResumeHardwareDeleteDialogComponent } from './delete/resume-hardware-delete-dialog.component';
import { ResumeHardwareRoutingModule } from './route/resume-hardware-routing.module';

@NgModule({
  imports: [SharedModule, ResumeHardwareRoutingModule],
  declarations: [
    ResumeHardwareComponent,
    ResumeHardwareDetailComponent,
    ResumeHardwareUpdateComponent,
    ResumeHardwareDeleteDialogComponent,
  ],
  entryComponents: [ResumeHardwareDeleteDialogComponent],
})
export class ResumeHardwareModule {}
