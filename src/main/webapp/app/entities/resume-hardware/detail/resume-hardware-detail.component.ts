import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResumeHardware } from '../resume-hardware.model';

@Component({
  selector: 'jhi-resume-hardware-detail',
  templateUrl: './resume-hardware-detail.component.html',
})
export class ResumeHardwareDetailComponent implements OnInit {
  resumeHardware: IResumeHardware | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeHardware }) => {
      this.resumeHardware = resumeHardware;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
