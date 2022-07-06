import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResumeSoftxComponent } from './list/resume-softx.component';
import { ResumeSoftxDetailComponent } from './detail/resume-softx-detail.component';
import { ResumeSoftxUpdateComponent } from './update/resume-softx-update.component';
import { ResumeSoftxDeleteDialogComponent } from './delete/resume-softx-delete-dialog.component';
import { ResumeSoftxRoutingModule } from './route/resume-softx-routing.module';

@NgModule({
  imports: [SharedModule, ResumeSoftxRoutingModule],
  declarations: [ResumeSoftxComponent, ResumeSoftxDetailComponent, ResumeSoftxUpdateComponent, ResumeSoftxDeleteDialogComponent],
  entryComponents: [ResumeSoftxDeleteDialogComponent],
})
export class ResumeSoftxModule {}
