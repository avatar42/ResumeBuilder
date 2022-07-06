import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IResumeSoftware, ResumeSoftware } from '../resume-software.model';
import { ResumeSoftwareService } from '../service/resume-software.service';
import { IResumeSoftx } from 'app/entities/resume-softx/resume-softx.model';
import { ResumeSoftxService } from 'app/entities/resume-softx/service/resume-softx.service';

@Component({
  selector: 'jhi-resume-software-update',
  templateUrl: './resume-software-update.component.html',
})
export class ResumeSoftwareUpdateComponent implements OnInit {
  isSaving = false;

  resumeSoftwaresSharedCollection: IResumeSoftware[] = [];
  resumeSoftxesSharedCollection: IResumeSoftx[] = [];

  editForm = this.fb.group({
    id: [],
    softwareName: [null, [Validators.required]],
    softwareVer: [null, [Validators.required]],
    softwareCatId: [null, [Validators.required]],
    summarryCatId: [null, [Validators.required]],
    resumeSoftx: [],
    resumeSoftware: [],
  });

  constructor(
    protected resumeSoftwareService: ResumeSoftwareService,
    protected resumeSoftxService: ResumeSoftxService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeSoftware }) => {
      this.updateForm(resumeSoftware);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resumeSoftware = this.createFromForm();
    if (resumeSoftware.id !== undefined) {
      this.subscribeToSaveResponse(this.resumeSoftwareService.update(resumeSoftware));
    } else {
      this.subscribeToSaveResponse(this.resumeSoftwareService.create(resumeSoftware));
    }
  }

  trackResumeSoftwareById(_index: number, item: IResumeSoftware): number {
    return item.id!;
  }

  trackResumeSoftxById(_index: number, item: IResumeSoftx): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResumeSoftware>>): void {
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

  protected updateForm(resumeSoftware: IResumeSoftware): void {
    this.editForm.patchValue({
      id: resumeSoftware.id,
      softwareName: resumeSoftware.softwareName,
      softwareVer: resumeSoftware.softwareVer,
      softwareCatId: resumeSoftware.softwareCatId,
      summarryCatId: resumeSoftware.summarryCatId,
      resumeSoftx: resumeSoftware.resumeSoftx,
      resumeSoftware: resumeSoftware.resumeSoftware,
    });

    this.resumeSoftwaresSharedCollection = this.resumeSoftwareService.addResumeSoftwareToCollectionIfMissing(
      this.resumeSoftwaresSharedCollection,
      resumeSoftware.resumeSoftware
    );
    this.resumeSoftxesSharedCollection = this.resumeSoftxService.addResumeSoftxToCollectionIfMissing(
      this.resumeSoftxesSharedCollection,
      resumeSoftware.resumeSoftx
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

    this.resumeSoftxService
      .query()
      .pipe(map((res: HttpResponse<IResumeSoftx[]>) => res.body ?? []))
      .pipe(
        map((resumeSoftxes: IResumeSoftx[]) =>
          this.resumeSoftxService.addResumeSoftxToCollectionIfMissing(resumeSoftxes, this.editForm.get('resumeSoftx')!.value)
        )
      )
      .subscribe((resumeSoftxes: IResumeSoftx[]) => (this.resumeSoftxesSharedCollection = resumeSoftxes));
  }

  protected createFromForm(): IResumeSoftware {
    return {
      ...new ResumeSoftware(),
      id: this.editForm.get(['id'])!.value,
      softwareName: this.editForm.get(['softwareName'])!.value,
      softwareVer: this.editForm.get(['softwareVer'])!.value,
      softwareCatId: this.editForm.get(['softwareCatId'])!.value,
      summarryCatId: this.editForm.get(['summarryCatId'])!.value,
      resumeSoftx: this.editForm.get(['resumeSoftx'])!.value,
      resumeSoftware: this.editForm.get(['resumeSoftware'])!.value,
    };
  }
}
