import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResumeJobsService } from '../service/resume-jobs.service';
import { IResumeJobs, ResumeJobs } from '../resume-jobs.model';
import { IResumeHardx } from 'app/entities/resume-hardx/resume-hardx.model';
import { ResumeHardxService } from 'app/entities/resume-hardx/service/resume-hardx.service';
import { IResumeSoftx } from 'app/entities/resume-softx/resume-softx.model';
import { ResumeSoftxService } from 'app/entities/resume-softx/service/resume-softx.service';
import { IResumeRespon } from 'app/entities/resume-respon/resume-respon.model';
import { ResumeResponService } from 'app/entities/resume-respon/service/resume-respon.service';

import { ResumeJobsUpdateComponent } from './resume-jobs-update.component';

describe('ResumeJobs Management Update Component', () => {
  let comp: ResumeJobsUpdateComponent;
  let fixture: ComponentFixture<ResumeJobsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let resumeJobsService: ResumeJobsService;
  let resumeHardxService: ResumeHardxService;
  let resumeSoftxService: ResumeSoftxService;
  let resumeResponService: ResumeResponService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResumeJobsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ResumeJobsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumeJobsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resumeJobsService = TestBed.inject(ResumeJobsService);
    resumeHardxService = TestBed.inject(ResumeHardxService);
    resumeSoftxService = TestBed.inject(ResumeSoftxService);
    resumeResponService = TestBed.inject(ResumeResponService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ResumeHardx query and add missing value', () => {
      const resumeJobs: IResumeJobs = { id: 456 };
      const resumeHardx: IResumeHardx = { id: 96967 };
      resumeJobs.resumeHardx = resumeHardx;

      const resumeHardxCollection: IResumeHardx[] = [{ id: 9039 }];
      jest.spyOn(resumeHardxService, 'query').mockReturnValue(of(new HttpResponse({ body: resumeHardxCollection })));
      const additionalResumeHardxes = [resumeHardx];
      const expectedCollection: IResumeHardx[] = [...additionalResumeHardxes, ...resumeHardxCollection];
      jest.spyOn(resumeHardxService, 'addResumeHardxToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resumeJobs });
      comp.ngOnInit();

      expect(resumeHardxService.query).toHaveBeenCalled();
      expect(resumeHardxService.addResumeHardxToCollectionIfMissing).toHaveBeenCalledWith(
        resumeHardxCollection,
        ...additionalResumeHardxes
      );
      expect(comp.resumeHardxesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ResumeSoftx query and add missing value', () => {
      const resumeJobs: IResumeJobs = { id: 456 };
      const resumeSoftx: IResumeSoftx = { id: 41931 };
      resumeJobs.resumeSoftx = resumeSoftx;

      const resumeSoftxCollection: IResumeSoftx[] = [{ id: 66042 }];
      jest.spyOn(resumeSoftxService, 'query').mockReturnValue(of(new HttpResponse({ body: resumeSoftxCollection })));
      const additionalResumeSoftxes = [resumeSoftx];
      const expectedCollection: IResumeSoftx[] = [...additionalResumeSoftxes, ...resumeSoftxCollection];
      jest.spyOn(resumeSoftxService, 'addResumeSoftxToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resumeJobs });
      comp.ngOnInit();

      expect(resumeSoftxService.query).toHaveBeenCalled();
      expect(resumeSoftxService.addResumeSoftxToCollectionIfMissing).toHaveBeenCalledWith(
        resumeSoftxCollection,
        ...additionalResumeSoftxes
      );
      expect(comp.resumeSoftxesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ResumeRespon query and add missing value', () => {
      const resumeJobs: IResumeJobs = { id: 456 };
      const resumeRespon: IResumeRespon = { id: 30712 };
      resumeJobs.resumeRespon = resumeRespon;

      const resumeResponCollection: IResumeRespon[] = [{ id: 75778 }];
      jest.spyOn(resumeResponService, 'query').mockReturnValue(of(new HttpResponse({ body: resumeResponCollection })));
      const additionalResumeRespons = [resumeRespon];
      const expectedCollection: IResumeRespon[] = [...additionalResumeRespons, ...resumeResponCollection];
      jest.spyOn(resumeResponService, 'addResumeResponToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resumeJobs });
      comp.ngOnInit();

      expect(resumeResponService.query).toHaveBeenCalled();
      expect(resumeResponService.addResumeResponToCollectionIfMissing).toHaveBeenCalledWith(
        resumeResponCollection,
        ...additionalResumeRespons
      );
      expect(comp.resumeResponsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const resumeJobs: IResumeJobs = { id: 456 };
      const resumeHardx: IResumeHardx = { id: 16936 };
      resumeJobs.resumeHardx = resumeHardx;
      const resumeSoftx: IResumeSoftx = { id: 78090 };
      resumeJobs.resumeSoftx = resumeSoftx;
      const resumeRespon: IResumeRespon = { id: 61620 };
      resumeJobs.resumeRespon = resumeRespon;

      activatedRoute.data = of({ resumeJobs });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(resumeJobs));
      expect(comp.resumeHardxesSharedCollection).toContain(resumeHardx);
      expect(comp.resumeSoftxesSharedCollection).toContain(resumeSoftx);
      expect(comp.resumeResponsSharedCollection).toContain(resumeRespon);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeJobs>>();
      const resumeJobs = { id: 123 };
      jest.spyOn(resumeJobsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeJobs });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeJobs }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(resumeJobsService.update).toHaveBeenCalledWith(resumeJobs);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeJobs>>();
      const resumeJobs = new ResumeJobs();
      jest.spyOn(resumeJobsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeJobs });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeJobs }));
      saveSubject.complete();

      // THEN
      expect(resumeJobsService.create).toHaveBeenCalledWith(resumeJobs);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeJobs>>();
      const resumeJobs = { id: 123 };
      jest.spyOn(resumeJobsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeJobs });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resumeJobsService.update).toHaveBeenCalledWith(resumeJobs);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackResumeHardxById', () => {
      it('Should return tracked ResumeHardx primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackResumeHardxById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackResumeSoftxById', () => {
      it('Should return tracked ResumeSoftx primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackResumeSoftxById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackResumeResponById', () => {
      it('Should return tracked ResumeRespon primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackResumeResponById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
