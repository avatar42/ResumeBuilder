import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeSummary } from '../resume-summary.model';
import { ResumeSummaryService } from '../service/resume-summary.service';
import { ResumeSummaryDeleteDialogComponent } from '../delete/resume-summary-delete-dialog.component';

@Component({
  selector: 'jhi-resume-summary',
  templateUrl: './resume-summary.component.html',
})
export class ResumeSummaryComponent implements OnInit {
  resumeSummaries?: IResumeSummary[];
  isLoading = false;

  constructor(protected resumeSummaryService: ResumeSummaryService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.resumeSummaryService.query().subscribe({
      next: (res: HttpResponse<IResumeSummary[]>) => {
        this.isLoading = false;
        this.resumeSummaries = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IResumeSummary): number {
    return item.id!;
  }

  delete(resumeSummary: IResumeSummary): void {
    const modalRef = this.modalService.open(ResumeSummaryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resumeSummary = resumeSummary;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
