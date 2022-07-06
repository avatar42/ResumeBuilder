import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeHardware } from '../resume-hardware.model';
import { ResumeHardwareService } from '../service/resume-hardware.service';
import { ResumeHardwareDeleteDialogComponent } from '../delete/resume-hardware-delete-dialog.component';

@Component({
  selector: 'jhi-resume-hardware',
  templateUrl: './resume-hardware.component.html',
})
export class ResumeHardwareComponent implements OnInit {
  resumeHardwares?: IResumeHardware[];
  isLoading = false;

  constructor(protected resumeHardwareService: ResumeHardwareService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.resumeHardwareService.query().subscribe({
      next: (res: HttpResponse<IResumeHardware[]>) => {
        this.isLoading = false;
        this.resumeHardwares = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IResumeHardware): number {
    return item.id!;
  }

  delete(resumeHardware: IResumeHardware): void {
    const modalRef = this.modalService.open(ResumeHardwareDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resumeHardware = resumeHardware;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
