import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeSummary } from '../resume-summary.model';
import { ResumeSummaryService } from '../service/resume-summary.service';

@Component({
  templateUrl: './resume-summary-delete-dialog.component.html',
})
export class ResumeSummaryDeleteDialogComponent {
  resumeSummary?: IResumeSummary;

  constructor(protected resumeSummaryService: ResumeSummaryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resumeSummaryService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
