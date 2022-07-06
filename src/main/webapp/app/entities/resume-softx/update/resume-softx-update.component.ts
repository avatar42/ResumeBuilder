import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IResumeSoftx, ResumeSoftx } from '../resume-softx.model';
import { ResumeSoftxService } from '../service/resume-softx.service';

@Component({
  selector: 'jhi-resume-softx-update',
  templateUrl: './resume-softx-update.component.html',
})
export class ResumeSoftxUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected resumeSoftxService: ResumeSoftxService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeSoftx }) => {
      this.updateForm(resumeSoftx);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resumeSoftx = this.createFromForm();
    if (resumeSoftx.id !== undefined) {
      this.subscribeToSaveResponse(this.resumeSoftxService.update(resumeSoftx));
    } else {
      this.subscribeToSaveResponse(this.resumeSoftxService.create(resumeSoftx));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResumeSoftx>>): void {
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

  protected updateForm(resumeSoftx: IResumeSoftx): void {
    this.editForm.patchValue({
      id: resumeSoftx.id,
    });
  }

  protected createFromForm(): IResumeSoftx {
    return {
      ...new ResumeSoftx(),
      id: this.editForm.get(['id'])!.value,
    };
  }
}
