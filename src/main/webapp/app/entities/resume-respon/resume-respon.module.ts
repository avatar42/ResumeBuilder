import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResumeResponComponent } from './list/resume-respon.component';
import { ResumeResponDetailComponent } from './detail/resume-respon-detail.component';
import { ResumeResponUpdateComponent } from './update/resume-respon-update.component';
import { ResumeResponDeleteDialogComponent } from './delete/resume-respon-delete-dialog.component';
import { ResumeResponRoutingModule } from './route/resume-respon-routing.module';

@NgModule({
  imports: [SharedModule, ResumeResponRoutingModule],
  declarations: [ResumeResponComponent, ResumeResponDetailComponent, ResumeResponUpdateComponent, ResumeResponDeleteDialogComponent],
  entryComponents: [ResumeResponDeleteDialogComponent],
})
export class ResumeResponModule {}
