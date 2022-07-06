import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResumeResponTypeService } from '../service/resume-respon-type.service';
import { IResumeResponType, ResumeResponType } from '../resume-respon-type.model';

import { ResumeResponTypeUpdateComponent } from './resume-respon-type-update.component';

describe('ResumeResponType Management Update Component', () => {
  let comp: ResumeResponTypeUpdateComponent;
  let fixture: ComponentFixture<ResumeResponTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let resumeResponTypeService: ResumeResponTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResumeResponTypeUpdateComponent],
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
      .overrideTemplate(ResumeResponTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumeResponTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resumeResponTypeService = TestBed.inject(ResumeResponTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const resumeResponType: IResumeResponType = { id: 456 };

      activatedRoute.data = of({ resumeResponType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(resumeResponType));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeResponType>>();
      const resumeResponType = { id: 123 };
      jest.spyOn(resumeResponTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeResponType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeResponType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(resumeResponTypeService.update).toHaveBeenCalledWith(resumeResponType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeResponType>>();
      const resumeResponType = new ResumeResponType();
      jest.spyOn(resumeResponTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeResponType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeResponType }));
      saveSubject.complete();

      // THEN
      expect(resumeResponTypeService.create).toHaveBeenCalledWith(resumeResponType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeResponType>>();
      const resumeResponType = { id: 123 };
      jest.spyOn(resumeResponTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeResponType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resumeResponTypeService.update).toHaveBeenCalledWith(resumeResponType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
