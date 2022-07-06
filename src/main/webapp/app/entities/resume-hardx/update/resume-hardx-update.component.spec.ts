import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResumeHardxService } from '../service/resume-hardx.service';
import { IResumeHardx, ResumeHardx } from '../resume-hardx.model';

import { ResumeHardxUpdateComponent } from './resume-hardx-update.component';

describe('ResumeHardx Management Update Component', () => {
  let comp: ResumeHardxUpdateComponent;
  let fixture: ComponentFixture<ResumeHardxUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let resumeHardxService: ResumeHardxService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResumeHardxUpdateComponent],
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
      .overrideTemplate(ResumeHardxUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumeHardxUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resumeHardxService = TestBed.inject(ResumeHardxService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const resumeHardx: IResumeHardx = { id: 456 };

      activatedRoute.data = of({ resumeHardx });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(resumeHardx));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeHardx>>();
      const resumeHardx = { id: 123 };
      jest.spyOn(resumeHardxService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeHardx });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeHardx }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(resumeHardxService.update).toHaveBeenCalledWith(resumeHardx);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeHardx>>();
      const resumeHardx = new ResumeHardx();
      jest.spyOn(resumeHardxService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeHardx });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeHardx }));
      saveSubject.complete();

      // THEN
      expect(resumeHardxService.create).toHaveBeenCalledWith(resumeHardx);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeHardx>>();
      const resumeHardx = { id: 123 };
      jest.spyOn(resumeHardxService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeHardx });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resumeHardxService.update).toHaveBeenCalledWith(resumeHardx);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
