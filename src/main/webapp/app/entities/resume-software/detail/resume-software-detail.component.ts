import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResumeSoftware } from '../resume-software.model';

@Component({
  selector: 'jhi-resume-software-detail',
  templateUrl: './resume-software-detail.component.html',
})
export class ResumeSoftwareDetailComponent implements OnInit {
  resumeSoftware: IResumeSoftware | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeSoftware }) => {
      this.resumeSoftware = resumeSoftware;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
