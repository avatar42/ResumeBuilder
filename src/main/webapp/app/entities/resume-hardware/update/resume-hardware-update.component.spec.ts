import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResumeHardwareService } from '../service/resume-hardware.service';
import { IResumeHardware, ResumeHardware } from '../resume-hardware.model';
import { IResumeHardx } from 'app/entities/resume-hardx/resume-hardx.model';
import { ResumeHardxService } from 'app/entities/resume-hardx/service/resume-hardx.service';

import { ResumeHardwareUpdateComponent } from './resume-hardware-update.component';

describe('ResumeHardware Management Update Component', () => {
  let comp: ResumeHardwareUpdateComponent;
  let fixture: ComponentFixture<ResumeHardwareUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let resumeHardwareService: ResumeHardwareService;
  let resumeHardxService: ResumeHardxService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResumeHardwareUpdateComponent],
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
      .overrideTemplate(ResumeHardwareUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumeHardwareUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resumeHardwareService = TestBed.inject(ResumeHardwareService);
    resumeHardxService = TestBed.inject(ResumeHardxService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ResumeHardx query and add missing value', () => {
      const resumeHardware: IResumeHardware = { id: 456 };
      const resumeHardx: IResumeHardx = { id: 7951 };
      resumeHardware.resumeHardx = resumeHardx;

      const resumeHardxCollection: IResumeHardx[] = [{ id: 25630 }];
      jest.spyOn(resumeHardxService, 'query').mockReturnValue(of(new HttpResponse({ body: resumeHardxCollection })));
      const additionalResumeHardxes = [resumeHardx];
      const expectedCollection: IResumeHardx[] = [...additionalResumeHardxes, ...resumeHardxCollection];
      jest.spyOn(resumeHardxService, 'addResumeHardxToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resumeHardware });
      comp.ngOnInit();

      expect(resumeHardxService.query).toHaveBeenCalled();
      expect(resumeHardxService.addResumeHardxToCollectionIfMissing).toHaveBeenCalledWith(
        resumeHardxCollection,
        ...additionalResumeHardxes
      );
      expect(comp.resumeHardxesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const resumeHardware: IResumeHardware = { id: 456 };
      const resumeHardx: IResumeHardx = { id: 63639 };
      resumeHardware.resumeHardx = resumeHardx;

      activatedRoute.data = of({ resumeHardware });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(resumeHardware));
      expect(comp.resumeHardxesSharedCollection).toContain(resumeHardx);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeHardware>>();
      const resumeHardware = { id: 123 };
      jest.spyOn(resumeHardwareService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeHardware });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeHardware }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(resumeHardwareService.update).toHaveBeenCalledWith(resumeHardware);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeHardware>>();
      const resumeHardware = new ResumeHardware();
      jest.spyOn(resumeHardwareService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeHardware });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeHardware }));
      saveSubject.complete();

      // THEN
      expect(resumeHardwareService.create).toHaveBeenCalledWith(resumeHardware);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeHardware>>();
      const resumeHardware = { id: 123 };
      jest.spyOn(resumeHardwareService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeHardware });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resumeHardwareService.update).toHaveBeenCalledWith(resumeHardware);
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
  });
});
