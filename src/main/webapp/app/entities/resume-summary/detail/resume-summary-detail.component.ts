import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResumeSummary } from '../resume-summary.model';

@Component({
  selector: 'jhi-resume-summary-detail',
  templateUrl: './resume-summary-detail.component.html',
})
export class ResumeSummaryDetailComponent implements OnInit {
  resumeSummary: IResumeSummary | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeSummary }) => {
      this.resumeSummary = resumeSummary;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
