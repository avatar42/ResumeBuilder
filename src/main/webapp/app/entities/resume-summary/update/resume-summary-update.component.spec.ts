import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResumeSummaryService } from '../service/resume-summary.service';
import { IResumeSummary, ResumeSummary } from '../resume-summary.model';
import { IResumeSoftware } from 'app/entities/resume-software/resume-software.model';
import { ResumeSoftwareService } from 'app/entities/resume-software/service/resume-software.service';

import { ResumeSummaryUpdateComponent } from './resume-summary-update.component';

describe('ResumeSummary Management Update Component', () => {
  let comp: ResumeSummaryUpdateComponent;
  let fixture: ComponentFixture<ResumeSummaryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let resumeSummaryService: ResumeSummaryService;
  let resumeSoftwareService: ResumeSoftwareService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResumeSummaryUpdateComponent],
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
      .overrideTemplate(ResumeSummaryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumeSummaryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resumeSummaryService = TestBed.inject(ResumeSummaryService);
    resumeSoftwareService = TestBed.inject(ResumeSoftwareService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ResumeSoftware query and add missing value', () => {
      const resumeSummary: IResumeSummary = { id: 456 };
      const resumeSoftware: IResumeSoftware = { id: 59843 };
      resumeSummary.resumeSoftware = resumeSoftware;

      const resumeSoftwareCollection: IResumeSoftware[] = [{ id: 2635 }];
      jest.spyOn(resumeSoftwareService, 'query').mockReturnValue(of(new HttpResponse({ body: resumeSoftwareCollection })));
      const additionalResumeSoftwares = [resumeSoftware];
      const expectedCollection: IResumeSoftware[] = [...additionalResumeSoftwares, ...resumeSoftwareCollection];
      jest.spyOn(resumeSoftwareService, 'addResumeSoftwareToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resumeSummary });
      comp.ngOnInit();

      expect(resumeSoftwareService.query).toHaveBeenCalled();
      expect(resumeSoftwareService.addResumeSoftwareToCollectionIfMissing).toHaveBeenCalledWith(
        resumeSoftwareCollection,
        ...additionalResumeSoftwares
      );
      expect(comp.resumeSoftwaresSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const resumeSummary: IResumeSummary = { id: 456 };
      const resumeSoftware: IResumeSoftware = { id: 47770 };
      resumeSummary.resumeSoftware = resumeSoftware;

      activatedRoute.data = of({ resumeSummary });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(resumeSummary));
      expect(comp.resumeSoftwaresSharedCollection).toContain(resumeSoftware);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeSummary>>();
      const resumeSummary = { id: 123 };
      jest.spyOn(resumeSummaryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeSummary });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeSummary }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(resumeSummaryService.update).toHaveBeenCalledWith(resumeSummary);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeSummary>>();
      const resumeSummary = new ResumeSummary();
      jest.spyOn(resumeSummaryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeSummary });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeSummary }));
      saveSubject.complete();

      // THEN
      expect(resumeSummaryService.create).toHaveBeenCalledWith(resumeSummary);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeSummary>>();
      const resumeSummary = { id: 123 };
      jest.spyOn(resumeSummaryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeSummary });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resumeSummaryService.update).toHaveBeenCalledWith(resumeSummary);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackResumeSoftwareById', () => {
      it('Should return tracked ResumeSoftware primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackResumeSoftwareById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
