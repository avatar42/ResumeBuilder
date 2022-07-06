import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeResponType } from '../resume-respon-type.model';
import { ResumeResponTypeService } from '../service/resume-respon-type.service';

@Component({
  templateUrl: './resume-respon-type-delete-dialog.component.html',
})
export class ResumeResponTypeDeleteDialogComponent {
  resumeResponType?: IResumeResponType;

  constructor(protected resumeResponTypeService: ResumeResponTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resumeResponTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
