import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResumeRespon } from '../resume-respon.model';

@Component({
  selector: 'jhi-resume-respon-detail',
  templateUrl: './resume-respon-detail.component.html',
})
export class ResumeResponDetailComponent implements OnInit {
  resumeRespon: IResumeRespon | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeRespon }) => {
      this.resumeRespon = resumeRespon;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
