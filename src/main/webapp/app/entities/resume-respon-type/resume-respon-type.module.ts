import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResumeResponTypeComponent } from './list/resume-respon-type.component';
import { ResumeResponTypeDetailComponent } from './detail/resume-respon-type-detail.component';
import { ResumeResponTypeUpdateComponent } from './update/resume-respon-type-update.component';
import { ResumeResponTypeDeleteDialogComponent } from './delete/resume-respon-type-delete-dialog.component';
import { ResumeResponTypeRoutingModule } from './route/resume-respon-type-routing.module';

@NgModule({
  imports: [SharedModule, ResumeResponTypeRoutingModule],
  declarations: [
    ResumeResponTypeComponent,
    ResumeResponTypeDetailComponent,
    ResumeResponTypeUpdateComponent,
    ResumeResponTypeDeleteDialogComponent,
  ],
  entryComponents: [ResumeResponTypeDeleteDialogComponent],
})
export class ResumeResponTypeModule {}
