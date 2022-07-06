import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeJobs } from '../resume-jobs.model';
import { ResumeJobsService } from '../service/resume-jobs.service';

@Component({
  templateUrl: './resume-jobs-delete-dialog.component.html',
})
export class ResumeJobsDeleteDialogComponent {
  resumeJobs?: IResumeJobs;

  constructor(protected resumeJobsService: ResumeJobsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resumeJobsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
