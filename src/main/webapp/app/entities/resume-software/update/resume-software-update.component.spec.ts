import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResumeSoftwareService } from '../service/resume-software.service';
import { IResumeSoftware, ResumeSoftware } from '../resume-software.model';
import { IResumeSoftx } from 'app/entities/resume-softx/resume-softx.model';
import { ResumeSoftxService } from 'app/entities/resume-softx/service/resume-softx.service';

import { ResumeSoftwareUpdateComponent } from './resume-software-update.component';

describe('ResumeSoftware Management Update Component', () => {
  let comp: ResumeSoftwareUpdateComponent;
  let fixture: ComponentFixture<ResumeSoftwareUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let resumeSoftwareService: ResumeSoftwareService;
  let resumeSoftxService: ResumeSoftxService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResumeSoftwareUpdateComponent],
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
      .overrideTemplate(ResumeSoftwareUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumeSoftwareUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resumeSoftwareService = TestBed.inject(ResumeSoftwareService);
    resumeSoftxService = TestBed.inject(ResumeSoftxService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ResumeSoftware query and add missing value', () => {
      const resumeSoftware: IResumeSoftware = { id: 456 };
      const resumeSoftware: IResumeSoftware = { id: 5892 };
      resumeSoftware.resumeSoftware = resumeSoftware;

      const resumeSoftwareCollection: IResumeSoftware[] = [{ id: 43139 }];
      jest.spyOn(resumeSoftwareService, 'query').mockReturnValue(of(new HttpResponse({ body: resumeSoftwareCollection })));
      const additionalResumeSoftwares = [resumeSoftware];
      const expectedCollection: IResumeSoftware[] = [...additionalResumeSoftwares, ...resumeSoftwareCollection];
      jest.spyOn(resumeSoftwareService, 'addResumeSoftwareToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resumeSoftware });
      comp.ngOnInit();

      expect(resumeSoftwareService.query).toHaveBeenCalled();
      expect(resumeSoftwareService.addResumeSoftwareToCollectionIfMissing).toHaveBeenCalledWith(
        resumeSoftwareCollection,
        ...additionalResumeSoftwares
      );
      expect(comp.resumeSoftwaresSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ResumeSoftx query and add missing value', () => {
      const resumeSoftware: IResumeSoftware = { id: 456 };
      const resumeSoftx: IResumeSoftx = { id: 3369 };
      resumeSoftware.resumeSoftx = resumeSoftx;

      const resumeSoftxCollection: IResumeSoftx[] = [{ id: 1758 }];
      jest.spyOn(resumeSoftxService, 'query').mockReturnValue(of(new HttpResponse({ body: resumeSoftxCollection })));
      const additionalResumeSoftxes = [resumeSoftx];
      const expectedCollection: IResumeSoftx[] = [...additionalResumeSoftxes, ...resumeSoftxCollection];
      jest.spyOn(resumeSoftxService, 'addResumeSoftxToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resumeSoftware });
      comp.ngOnInit();

      expect(resumeSoftxService.query).toHaveBeenCalled();
      expect(resumeSoftxService.addResumeSoftxToCollectionIfMissing).toHaveBeenCalledWith(
        resumeSoftxCollection,
        ...additionalResumeSoftxes
      );
      expect(comp.resumeSoftxesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const resumeSoftware: IResumeSoftware = { id: 456 };
      const resumeSoftware: IResumeSoftware = { id: 45976 };
      resumeSoftware.resumeSoftware = resumeSoftware;
      const resumeSoftx: IResumeSoftx = { id: 97375 };
      resumeSoftware.resumeSoftx = resumeSoftx;

      activatedRoute.data = of({ resumeSoftware });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(resumeSoftware));
      expect(comp.resumeSoftwaresSharedCollection).toContain(resumeSoftware);
      expect(comp.resumeSoftxesSharedCollection).toContain(resumeSoftx);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeSoftware>>();
      const resumeSoftware = { id: 123 };
      jest.spyOn(resumeSoftwareService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeSoftware });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeSoftware }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(resumeSoftwareService.update).toHaveBeenCalledWith(resumeSoftware);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeSoftware>>();
      const resumeSoftware = new ResumeSoftware();
      jest.spyOn(resumeSoftwareService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeSoftware });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumeSoftware }));
      saveSubject.complete();

      // THEN
      expect(resumeSoftwareService.create).toHaveBeenCalledWith(resumeSoftware);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResumeSoftware>>();
      const resumeSoftware = { id: 123 };
      jest.spyOn(resumeSoftwareService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumeSoftware });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resumeSoftwareService.update).toHaveBeenCalledWith(resumeSoftware);
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

    describe('trackResumeSoftxById', () => {
      it('Should return tracked ResumeSoftx primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackResumeSoftxById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
