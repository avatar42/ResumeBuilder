import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeRespon } from '../resume-respon.model';
import { ResumeResponService } from '../service/resume-respon.service';

@Component({
  templateUrl: './resume-respon-delete-dialog.component.html',
})
export class ResumeResponDeleteDialogComponent {
  resumeRespon?: IResumeRespon;

  constructor(protected resumeResponService: ResumeResponService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resumeResponService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
