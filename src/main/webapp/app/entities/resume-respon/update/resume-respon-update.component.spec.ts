import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResumeResponService } from '../service/resume-respon.service';
import { IResumeRespon, ResumeRespon } from '../resume-respon.model';

import { ResumeResponUpdateComponent } from './resume-respon-update.component';

describe('ResumeRespon Management Update Component', () => {
  let comp: ResumeResponUpdateComponent;
  let fixture: ComponentFixture<ResumeResponUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let resumeResponService: ResumeResponService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResumeResponUpdateComponent],
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
      .overrideTemplate(ResumeResponUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumeResponUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resumeResponService = TestBed.inject(ResumeResponService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const resumeRespon: IResumeRespon = { id: 456 };

      activatedRoute.data = of({ resumeRespon });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(resumeRespon));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeRespon>>();
      const resumeRespon = { id: 123 };
      jest.spyOn(resumeResponService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeRespon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeRespon }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(resumeResponService.update).toHaveBeenCalledWith(resumeRespon);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeRespon>>();
      const resumeRespon = new ResumeRespon();
      jest.spyOn(resumeResponService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeRespon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeRespon }));
      saveSubject.complete();

      // THEN
      expect(resumeResponService.create).toHaveBeenCalledWith(resumeRespon);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeRespon>>();
      const resumeRespon = { id: 123 };
      jest.spyOn(resumeResponService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeRespon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resumeResponService.update).toHaveBeenCalledWith(resumeRespon);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
