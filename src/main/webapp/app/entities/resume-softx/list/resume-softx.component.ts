import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeSoftx } from '../resume-softx.model';
import { ResumeSoftxService } from '../service/resume-softx.service';
import { ResumeSoftxDeleteDialogComponent } from '../delete/resume-softx-delete-dialog.component';

@Component({
  selector: 'jhi-resume-softx',
  templateUrl: './resume-softx.component.html',
})
export class ResumeSoftxComponent implements OnInit {
  resumeSoftxes?: IResumeSoftx[];
  isLoading = false;

  constructor(protected resumeSoftxService: ResumeSoftxService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.resumeSoftxService.query().subscribe({
      next: (res: HttpResponse<IResumeSoftx[]>) => {
        this.isLoading = false;
        this.resumeSoftxes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IResumeSoftx): number {
    return item.id!;
  }

  delete(resumeSoftx: IResumeSoftx): void {
    const modalRef = this.modalService.open(ResumeSoftxDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resumeSoftx = resumeSoftx;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
