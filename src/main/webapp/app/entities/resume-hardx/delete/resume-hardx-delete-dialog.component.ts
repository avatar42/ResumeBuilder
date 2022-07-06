import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeHardx } from '../resume-hardx.model';
import { ResumeHardxService } from '../service/resume-hardx.service';

@Component({
  templateUrl: './resume-hardx-delete-dialog.component.html',
})
export class ResumeHardxDeleteDialogComponent {
  resumeHardx?: IResumeHardx;

  constructor(protected resumeHardxService: ResumeHardxService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resumeHardxService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
