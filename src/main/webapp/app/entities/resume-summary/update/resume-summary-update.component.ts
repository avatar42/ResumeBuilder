import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IResumeSummary, ResumeSummary } from '../resume-summary.model';
import { ResumeSummaryService } from '../service/resume-summary.service';
import { IResumeSoftware } from 'app/entities/resume-software/resume-software.model';
import { ResumeSoftwareService } from 'app/entities/resume-software/service/resume-software.service';

@Component({
  selector: 'jhi-resume-summary-update',
  templateUrl: './resume-summary-update.component.html',
})
export class ResumeSummaryUpdateComponent implements OnInit {
  isSaving = false;

  resumeSoftwaresSharedCollection: IResumeSoftware[] = [];

  editForm = this.fb.group({
    id: [],
    summaryName: [null, [Validators.required]],
    summaryOrder: [null, [Validators.required]],
    resumeSoftware: [],
  });

  constructor(
    protected resumeSummaryService: ResumeSummaryService,
    protected resumeSoftwareService: ResumeSoftwareService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeSummary }) => {
      this.updateForm(resumeSummary);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resumeSummary = this.createFromForm();
    if (resumeSummary.id !== undefined) {
      this.subscribeToSaveResponse(this.resumeSummaryService.update(resumeSummary));
    } else {
      this.subscribeToSaveResponse(this.resumeSummaryService.create(resumeSummary));
    }
  }

  trackResumeSoftwareById(_index: number, item: IResumeSoftware): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResumeSummary>>): void {
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

  protected updateForm(resumeSummary: IResumeSummary): void {
    this.editForm.patchValue({
      id: resumeSummary.id,
      summaryName: resumeSummary.summaryName,
      summaryOrder: resumeSummary.summaryOrder,
      resumeSoftware: resumeSummary.resumeSoftware,
    });

    this.resumeSoftwaresSharedCollection = this.resumeSoftwareService.addResumeSoftwareToCollectionIfMissing(
      this.resumeSoftwaresSharedCollection,
      resumeSummary.resumeSoftware
    );
  }

  protected loadRelationshipsOptions(): void {
    this.resumeSoftwareService
      .query()
      .pipe(map((res: HttpResponse<IResumeSoftware[]>) => res.body ?? []))
      .pipe(
        map((resumeSoftwares: IResumeSoftware[]) =>
          this.resumeSoftwareService.addResumeSoftwareToCollectionIfMissing(resumeSoftwares, this.editForm.get('resumeSoftware')!.value)
        )
      )
      .subscribe((resumeSoftwares: IResumeSoftware[]) => (this.resumeSoftwaresSharedCollection = resumeSoftwares));
  }

  protected createFromForm(): IResumeSummary {
    return {
      ...new ResumeSummary(),
      id: this.editForm.get(['id'])!.value,
      summaryName: this.editForm.get(['summaryName'])!.value,
      summaryOrder: this.editForm.get(['summaryOrder'])!.value,
      resumeSoftware: this.editForm.get(['resumeSoftware'])!.value,
    };
  }
}
