import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumeSoftware } from '../resume-software.model';
import { ResumeSoftwareService } from '../service/resume-software.service';
import { ResumeSoftwareDeleteDialogComponent } from '../delete/resume-software-delete-dialog.component';

@Component({
  selector: 'jhi-resume-software',
  templateUrl: './resume-software.component.html',
})
export class ResumeSoftwareComponent implements OnInit {
  resumeSoftwares?: IResumeSoftware[];
  isLoading = false;

  constructor(protected resumeSoftwareService: ResumeSoftwareService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.resumeSoftwareService.query().subscribe({
      next: (res: HttpResponse<IResumeSoftware[]>) => {
        this.isLoading = false;
        this.resumeSoftwares = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IResumeSoftware): number {
    return item.id!;
  }

  delete(resumeSoftware: IResumeSoftware): void {
    const modalRef = this.modalService.open(ResumeSoftwareDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resumeSoftware = resumeSoftware;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
