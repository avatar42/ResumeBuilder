import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeHardware } from '../resume-hardware.model';
import { ResumeHardwareService } from '../service/resume-hardware.service';

@Component({
  templateUrl: './resume-hardware-delete-dialog.component.html',
})
export class ResumeHardwareDeleteDialogComponent {
  resumeHardware?: IResumeHardware;

  constructor(protected resumeHardwareService: ResumeHardwareService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resumeHardwareService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
