import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeHardx } from '../resume-hardx.model';
import { ResumeHardxService } from '../service/resume-hardx.service';
import { ResumeHardxDeleteDialogComponent } from '../delete/resume-hardx-delete-dialog.component';

@Component({
  selector: 'jhi-resume-hardx',
  templateUrl: './resume-hardx.component.html',
})
export class ResumeHardxComponent implements OnInit {
  resumeHardxes?: IResumeHardx[];
  isLoading = false;

  constructor(protected resumeHardxService: ResumeHardxService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.resumeHardxService.query().subscribe({
      next: (res: HttpResponse<IResumeHardx[]>) => {
        this.isLoading = false;
        this.resumeHardxes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IResumeHardx): number {
    return item.id!;
  }

  delete(resumeHardx: IResumeHardx): void {
    const modalRef = this.modalService.open(ResumeHardxDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resumeHardx = resumeHardx;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
