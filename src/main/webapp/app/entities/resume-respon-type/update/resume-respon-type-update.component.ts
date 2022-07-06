import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IResumeResponType, ResumeResponType } from '../resume-respon-type.model';
import { ResumeResponTypeService } from '../service/resume-respon-type.service';

@Component({
  selector: 'jhi-resume-respon-type-update',
  templateUrl: './resume-respon-type-update.component.html',
})
export class ResumeResponTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    description: [],
  });

  constructor(
    protected resumeResponTypeService: ResumeResponTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeResponType }) => {
      this.updateForm(resumeResponType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resumeResponType = this.createFromForm();
    if (resumeResponType.id !== undefined) {
      this.subscribeToSaveResponse(this.resumeResponTypeService.update(resumeResponType));
    } else {
      this.subscribeToSaveResponse(this.resumeResponTypeService.create(resumeResponType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResumeResponType>>): void {
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

  protected updateForm(resumeResponType: IResumeResponType): void {
    this.editForm.patchValue({
      id: resumeResponType.id,
      description: resumeResponType.description,
    });
  }

  protected createFromForm(): IResumeResponType {
    return {
      ...new ResumeResponType(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }
}
