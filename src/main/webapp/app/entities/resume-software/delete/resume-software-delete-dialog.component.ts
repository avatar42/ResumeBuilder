import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeSoftware } from '../resume-software.model';
import { ResumeSoftwareService } from '../service/resume-software.service';

@Component({
  templateUrl: './resume-software-delete-dialog.component.html',
})
export class ResumeSoftwareDeleteDialogComponent {
  resumeSoftware?: IResumeSoftware;

  constructor(protected resumeSoftwareService: ResumeSoftwareService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resumeSoftwareService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
