import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResumeHardx } from '../resume-hardx.model';

@Component({
  selector: 'jhi-resume-hardx-detail',
  templateUrl: './resume-hardx-detail.component.html',
})
export class ResumeHardxDetailComponent implements OnInit {
  resumeHardx: IResumeHardx | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeHardx }) => {
      this.resumeHardx = resumeHardx;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
