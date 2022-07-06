import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IResumeHardx, ResumeHardx } from '../resume-hardx.model';
import { ResumeHardxService } from '../service/resume-hardx.service';

@Component({
  selector: 'jhi-resume-hardx-update',
  templateUrl: './resume-hardx-update.component.html',
})
export class ResumeHardxUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected resumeHardxService: ResumeHardxService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeHardx }) => {
      this.updateForm(resumeHardx);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resumeHardx = this.createFromForm();
    if (resumeHardx.id !== undefined) {
      this.subscribeToSaveResponse(this.resumeHardxService.update(resumeHardx));
    } else {
      this.subscribeToSaveResponse(this.resumeHardxService.create(resumeHardx));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResumeHardx>>): void {
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

  protected updateForm(resumeHardx: IResumeHardx): void {
    this.editForm.patchValue({
      id: resumeHardx.id,
    });
  }

  protected createFromForm(): IResumeHardx {
    return {
      ...new ResumeHardx(),
      id: this.editForm.get(['id'])!.value,
    };
  }
}
