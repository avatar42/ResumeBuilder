import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IResumeHardware, ResumeHardware } from '../resume-hardware.model';
import { ResumeHardwareService } from '../service/resume-hardware.service';
import { IResumeHardx } from 'app/entities/resume-hardx/resume-hardx.model';
import { ResumeHardxService } from 'app/entities/resume-hardx/service/resume-hardx.service';

@Component({
  selector: 'jhi-resume-hardware-update',
  templateUrl: './resume-hardware-update.component.html',
})
export class ResumeHardwareUpdateComponent implements OnInit {
  isSaving = false;

  resumeHardxesSharedCollection: IResumeHardx[] = [];

  editForm = this.fb.group({
    id: [],
    repairCertified: [null, [Validators.required]],
    showSum: [null, [Validators.required]],
    hardwareName: [null, [Validators.required]],
    resumeHardx: [],
  });

  constructor(
    protected resumeHardwareService: ResumeHardwareService,
    protected resumeHardxService: ResumeHardxService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeHardware }) => {
      this.updateForm(resumeHardware);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resumeHardware = this.createFromForm();
    if (resumeHardware.id !== undefined) {
      this.subscribeToSaveResponse(this.resumeHardwareService.update(resumeHardware));
    } else {
      this.subscribeToSaveResponse(this.resumeHardwareService.create(resumeHardware));
    }
  }

  trackResumeHardxById(_index: number, item: IResumeHardx): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResumeHardware>>): void {
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

  protected updateForm(resumeHardware: IResumeHardware): void {
    this.editForm.patchValue({
      id: resumeHardware.id,
      repairCertified: resumeHardware.repairCertified,
      showSum: resumeHardware.showSum,
      hardwareName: resumeHardware.hardwareName,
      resumeHardx: resumeHardware.resumeHardx,
    });

    this.resumeHardxesSharedCollection = this.resumeHardxService.addResumeHardxToCollectionIfMissing(
      this.resumeHardxesSharedCollection,
      resumeHardware.resumeHardx
    );
  }

  protected loadRelationshipsOptions(): void {
    this.resumeHardxService
      .query()
      .pipe(map((res: HttpResponse<IResumeHardx[]>) => res.body ?? []))
      .pipe(
        map((resumeHardxes: IResumeHardx[]) =>
          this.resumeHardxService.addResumeHardxToCollectionIfMissing(resumeHardxes, this.editForm.get('resumeHardx')!.value)
        )
      )
      .subscribe((resumeHardxes: IResumeHardx[]) => (this.resumeHardxesSharedCollection = resumeHardxes));
  }

  protected createFromForm(): IResumeHardware {
    return {
      ...new ResumeHardware(),
      id: this.editForm.get(['id'])!.value,
      repairCertified: this.editForm.get(['repairCertified'])!.value,
      showSum: this.editForm.get(['showSum'])!.value,
      hardwareName: this.editForm.get(['hardwareName'])!.value,
      resumeHardx: this.editForm.get(['resumeHardx'])!.value,
    };
  }
}
