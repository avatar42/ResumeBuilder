import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeRespon } from '../resume-respon.model';
import { ResumeResponService } from '../service/resume-respon.service';
import { ResumeResponDeleteDialogComponent } from '../delete/resume-respon-delete-dialog.component';

@Component({
  selector: 'jhi-resume-respon',
  templateUrl: './resume-respon.component.html',
})
export class ResumeResponComponent implements OnInit {
  resumeRespons?: IResumeRespon[];
  isLoading = false;

  constructor(protected resumeResponService: ResumeResponService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.resumeResponService.query().subscribe({
      next: (res: HttpResponse<IResumeRespon[]>) => {
        this.isLoading = false;
        this.resumeRespons = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IResumeRespon): number {
    return item.id!;
  }

  delete(resumeRespon: IResumeRespon): void {
    const modalRef = this.modalService.open(ResumeResponDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resumeRespon = resumeRespon;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
