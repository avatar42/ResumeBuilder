import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResumeJobs } from '../resume-jobs.model';

@Component({
  selector: 'jhi-resume-jobs-detail',
  templateUrl: './resume-jobs-detail.component.html',
})
export class ResumeJobsDetailComponent implements OnInit {
  resumeJobs: IResumeJobs | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeJobs }) => {
      this.resumeJobs = resumeJobs;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
