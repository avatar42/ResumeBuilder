import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResumeSoftx } from '../resume-softx.model';

@Component({
  selector: 'jhi-resume-softx-detail',
  templateUrl: './resume-softx-detail.component.html',
})
export class ResumeSoftxDetailComponent implements OnInit {
  resumeSoftx: IResumeSoftx | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeSoftx }) => {
      this.resumeSoftx = resumeSoftx;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
