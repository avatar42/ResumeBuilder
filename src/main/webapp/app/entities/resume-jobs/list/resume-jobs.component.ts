import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeJobs } from '../resume-jobs.model';
import { ResumeJobsService } from '../service/resume-jobs.service';
import { ResumeJobsDeleteDialogComponent } from '../delete/resume-jobs-delete-dialog.component';

@Component({
  selector: 'jhi-resume-jobs',
  templateUrl: './resume-jobs.component.html',
})
export class ResumeJobsComponent implements OnInit {
  resumeJobs?: IResumeJobs[];
  isLoading = false;

  constructor(protected resumeJobsService: ResumeJobsService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.resumeJobsService.query().subscribe({
      next: (res: HttpResponse<IResumeJobs[]>) => {
        this.isLoading = false;
        this.resumeJobs = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IResumeJobs): number {
    return item.id!;
  }

  delete(resumeJobs: IResumeJobs): void {
    const modalRef = this.modalService.open(ResumeJobsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resumeJobs = resumeJobs;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
