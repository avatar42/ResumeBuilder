import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IResumeRespon, ResumeRespon } from '../resume-respon.model';
import { ResumeResponService } from '../service/resume-respon.service';

@Component({
  selector: 'jhi-resume-respon-update',
  templateUrl: './resume-respon-update.component.html',
})
export class ResumeResponUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    jobId: [null, [Validators.required]],
    responOrder: [null, [Validators.required]],
    responShow: [null, [Validators.required]],
  });

  constructor(protected resumeResponService: ResumeResponService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeRespon }) => {
      this.updateForm(resumeRespon);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resumeRespon = this.createFromForm();
    if (resumeRespon.id !== undefined) {
      this.subscribeToSaveResponse(this.resumeResponService.update(resumeRespon));
    } else {
      this.subscribeToSaveResponse(this.resumeResponService.create(resumeRespon));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResumeRespon>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(resumeRespon: IResumeRespon): void {
    this.editForm.patchValue({
      id: resumeRespon.id,
      jobId: resumeRespon.jobId,
      responOrder: resumeRespon.responOrder,
      responShow: resumeRespon.responShow,
    });
  }

  protected createFromForm(): IResumeRespon {
    return {
      ...new ResumeRespon(),
      id: this.editForm.get(['id'])!.value,
      jobId: this.editForm.get(['jobId'])!.value,
      responOrder: this.editForm.get(['responOrder'])!.value,
      responShow: this.editForm.get(['responShow'])!.value,
    };
  }
}
