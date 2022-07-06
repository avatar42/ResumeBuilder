import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResumeHardxComponent } from './list/resume-hardx.component';
import { ResumeHardxDetailComponent } from './detail/resume-hardx-detail.component';
import { ResumeHardxUpdateComponent } from './update/resume-hardx-update.component';
import { ResumeHardxDeleteDialogComponent } from './delete/resume-hardx-delete-dialog.component';
import { ResumeHardxRoutingModule } from './route/resume-hardx-routing.module';

@NgModule({
  imports: [SharedModule, ResumeHardxRoutingModule],
  declarations: [ResumeHardxComponent, ResumeHardxDetailComponent, ResumeHardxUpdateComponent, ResumeHardxDeleteDialogComponent],
  entryComponents: [ResumeHardxDeleteDialogComponent],
})
export class ResumeHardxModule {}
