import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IResumeJobs, ResumeJobs } from '../resume-jobs.model';
import { ResumeJobsService } from '../service/resume-jobs.service';
import { IResumeHardx } from 'app/entities/resume-hardx/resume-hardx.model';
import { ResumeHardxService } from 'app/entities/resume-hardx/service/resume-hardx.service';
import { IResumeSoftx } from 'app/entities/resume-softx/resume-softx.model';
import { ResumeSoftxService } from 'app/entities/resume-softx/service/resume-softx.service';
import { IResumeRespon } from 'app/entities/resume-respon/resume-respon.model';
import { ResumeResponService } from 'app/entities/resume-respon/service/resume-respon.service';

@Component({
  selector: 'jhi-resume-jobs-update',
  templateUrl: './resume-jobs-update.component.html',
})
export class ResumeJobsUpdateComponent implements OnInit {
  isSaving = false;

  resumeHardxesSharedCollection: IResumeHardx[] = [];
  resumeSoftxesSharedCollection: IResumeSoftx[] = [];
  resumeResponsSharedCollection: IResumeRespon[] = [];

  editForm = this.fb.group({
    id: [],
    startDate: [null, [Validators.required]],
    endDate: [],
    company: [null, [Validators.required]],
    workStreet: [],
    location: [null, [Validators.required]],
    workZip: [],
    title: [null, [Validators.required]],
    months: [],
    contract: [null, [Validators.required]],
    agency: [],
    mgr: [null, [Validators.required]],
    mgrPhone: [],
    pay: [],
    corpAddr: [],
    corpPhone: [],
    agencyAddr: [null, [Validators.required]],
    agencyPhone: [],
    resumeHardx: [],
    resumeSoftx: [],
    resumeRespon: [],
  });

  constructor(
    protected resumeJobsService: ResumeJobsService,
    protected resumeHardxService: ResumeHardxService,
    protected resumeSoftxService: ResumeSoftxService,
    protected resumeResponService: ResumeResponService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumeJobs }) => {
      this.updateForm(resumeJobs);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resumeJobs = this.createFromForm();
    if (resumeJobs.id !== undefined) {
      this.subscribeToSaveResponse(this.resumeJobsService.update(resumeJobs));
    } else {
      this.subscribeToSaveResponse(this.resumeJobsService.create(resumeJobs));
    }
  }

  trackResumeHardxById(_index: number, item: IResumeHardx): number {
    return item.id!;
  }

  trackResumeSoftxById(_index: number, item: IResumeSoftx): number {
    return item.id!;
  }

  trackResumeResponById(_index: number, item: IResumeRespon): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResumeJobs>>): void {
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

  protected updateForm(resumeJobs: IResumeJobs): void {
    this.editForm.patchValue({
      id: resumeJobs.id,
      startDate: resumeJobs.startDate,
      endDate: resumeJobs.endDate,
      company: resumeJobs.company,
      workStreet: resumeJobs.workStreet,
      location: resumeJobs.location,
      workZip: resumeJobs.workZip,
      title: resumeJobs.title,
      months: resumeJobs.months,
      contract: resumeJobs.contract,
      agency: resumeJobs.agency,
      mgr: resumeJobs.mgr,
      mgrPhone: resumeJobs.mgrPhone,
      pay: resumeJobs.pay,
      corpAddr: resumeJobs.corpAddr,
      corpPhone: resumeJobs.corpPhone,
      agencyAddr: resumeJobs.agencyAddr,
      agencyPhone: resumeJobs.agencyPhone,
      resumeHardx: resumeJobs.resumeHardx,
      resumeSoftx: resumeJobs.resumeSoftx,
      resumeRespon: resumeJobs.resumeRespon,
    });

    this.resumeHardxesSharedCollection = this.resumeHardxService.addResumeHardxToCollectionIfMissing(
      this.resumeHardxesSharedCollection,
      resumeJobs.resumeHardx
    );
    this.resumeSoftxesSharedCollection = this.resumeSoftxService.addResumeSoftxToCollectionIfMissing(
      this.resumeSoftxesSharedCollection,
      resumeJobs.resumeSoftx
    );
    this.resumeResponsSharedCollection = this.resumeResponService.addResumeResponToCollectionIfMissing(
      this.resumeResponsSharedCollection,
      resumeJobs.resumeRespon
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

    this.resumeSoftxService
      .query()
      .pipe(map((res: HttpResponse<IResumeSoftx[]>) => res.body ?? []))
      .pipe(
        map((resumeSoftxes: IResumeSoftx[]) =>
          this.resumeSoftxService.addResumeSoftxToCollectionIfMissing(resumeSoftxes, this.editForm.get('resumeSoftx')!.value)
        )
      )
      .subscribe((resumeSoftxes: IResumeSoftx[]) => (this.resumeSoftxesSharedCollection = resumeSoftxes));

    this.resumeResponService
      .query()
      .pipe(map((res: HttpResponse<IResumeRespon[]>) => res.body ?? []))
      .pipe(
        map((resumeRespons: IResumeRespon[]) =>
          this.resumeResponService.addResumeResponToCollectionIfMissing(resumeRespons, this.editForm.get('resumeRespon')!.value)
        )
      )
      .subscribe((resumeRespons: IResumeRespon[]) => (this.resumeResponsSharedCollection = resumeRespons));
  }

  protected createFromForm(): IResumeJobs {
    return {
      ...new ResumeJobs(),
      id: this.editForm.get(['id'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      company: this.editForm.get(['company'])!.value,
      workStreet: this.editForm.get(['workStreet'])!.value,
      location: this.editForm.get(['location'])!.value,
      workZip: this.editForm.get(['workZip'])!.value,
      title: this.editForm.get(['title'])!.value,
      months: this.editForm.get(['months'])!.value,
      contract: this.editForm.get(['contract'])!.value,
      agency: this.editForm.get(['agency'])!.value,
      mgr: this.editForm.get(['mgr'])!.value,
      mgrPhone: this.editForm.get(['mgrPhone'])!.value,
      pay: this.editForm.get(['pay'])!.value,
      corpAddr: this.editForm.get(['corpAddr'])!.value,
      corpPhone: this.editForm.get(['corpPhone'])!.value,
      agencyAddr: this.editForm.get(['agencyAddr'])!.value,
      agencyPhone: this.editForm.get(['agencyPhone'])!.value,
      resumeHardx: this.editForm.get(['resumeHardx'])!.value,
      resumeSoftx: this.editForm.get(['resumeSoftx'])!.value,
      resumeRespon: this.editForm.get(['resumeRespon'])!.value,
    };
  }
}
