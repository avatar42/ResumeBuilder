import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResumeResponType } from '../resume-respon-type.model';

@Component({
  selector: 'jhi-resume-respon-type-detail',
  templateUrl: './resume-respon-type-detail.component.html',
})
export class ResumeResponTypeDetailComponent implements OnInit {
  resumeResponType: IResumeResponType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeResponType }) => {
      this.resumeResponType = resumeResponType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
