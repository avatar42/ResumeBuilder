import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeResponType } from '../resume-respon-type.model';
import { ResumeResponTypeService } from '../service/resume-respon-type.service';
import { ResumeResponTypeDeleteDialogComponent } from '../delete/resume-respon-type-delete-dialog.component';

@Component({
  selector: 'jhi-resume-respon-type',
  templateUrl: './resume-respon-type.component.html',
})
export class ResumeResponTypeComponent implements OnInit {
  resumeResponTypes?: IResumeResponType[];
  isLoading = false;

  constructor(protected resumeResponTypeService: ResumeResponTypeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.resumeResponTypeService.query().subscribe({
      next: (res: HttpResponse<IResumeResponType[]>) => {
        this.isLoading = false;
        this.resumeResponTypes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IResumeResponType): number {
    return item.id!;
  }

  delete(resumeResponType: IResumeResponType): void {
    const modalRef = this.modalService.open(ResumeResponTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resumeResponType = resumeResponType;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
