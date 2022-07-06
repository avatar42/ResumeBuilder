import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeSoftx } from '../resume-softx.model';
import { ResumeSoftxService } from '../service/resume-softx.service';

@Component({
  templateUrl: './resume-softx-delete-dialog.component.html',
})
export class ResumeSoftxDeleteDialogComponent {
  resumeSoftx?: IResumeSoftx;

  constructor(protected resumeSoftxService: ResumeSoftxService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resumeSoftxService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
