import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResumeSoftxService } from '../service/resume-softx.service';
import { IResumeSoftx, ResumeSoftx } from '../resume-softx.model';

import { ResumeSoftxUpdateComponent } from './resume-softx-update.component';

describe('ResumeSoftx Management Update Component', () => {
  let comp: ResumeSoftxUpdateComponent;
  let fixture: ComponentFixture<ResumeSoftxUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let resumeSoftxService: ResumeSoftxService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResumeSoftxUpdateComponent],
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
      .overrideTemplate(ResumeSoftxUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumeSoftxUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resumeSoftxService = TestBed.inject(ResumeSoftxService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const resumeSoftx: IResumeSoftx = { id: 456 };

      activatedRoute.data = of({ resumeSoftx });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(resumeSoftx));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeSoftx>>();
      const resumeSoftx = { id: 123 };
      jest.spyOn(resumeSoftxService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeSoftx });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeSoftx }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(resumeSoftxService.update).toHaveBeenCalledWith(resumeSoftx);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeSoftx>>();
      const resumeSoftx = new ResumeSoftx();
      jest.spyOn(resumeSoftxService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeSoftx });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeSoftx }));
      saveSubject.complete();

      // THEN
      expect(resumeSoftxService.create).toHaveBeenCalledWith(resumeSoftx);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeSoftx>>();
      const resumeSoftx = { id: 123 };
      jest.spyOn(resumeSoftxService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeSoftx });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resumeSoftxService.update).toHaveBeenCalledWith(resumeSoftx);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
