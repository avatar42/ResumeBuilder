import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IResumeHardx, ResumeHardx } from '../resume-hardx.model';

import { ResumeHardxService } from './resume-hardx.service';

describe('ResumeHardx Service', () => {
  let service: ResumeHardxService;
  let httpMock: HttpTestingController;
  let elemDefault: IResumeHardx;
  let expectedResult: IResumeHardx | IResumeHardx[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResumeHardxService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ResumeHardx', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ResumeHardx()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResumeHardx', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResumeHardx', () => {
      const patchObject = Object.assign({}, new ResumeHardx());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResumeHardx', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ResumeHardx', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addResumeHardxToCollectionIfMissing', () => {
      it('should add a ResumeHardx to an empty array', () => {
        const resumeHardx: IResumeHardx = { id: 123 };
        expectedResult = service.addResumeHardxToCollectionIfMissing([], resumeHardx);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeHardx);
      });

      it('should not add a ResumeHardx to an array that contains it', () => {
        const resumeHardx: IResumeHardx = { id: 123 };
        const resumeHardxCollection: IResumeHardx[] = [
          {
            ...resumeHardx,
          },
          { id: 456 },
        ];
        expectedResult = service.addResumeHardxToCollectionIfMissing(resumeHardxCollection, resumeHardx);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResumeHardx to an array that doesn't contain it", () => {
        const resumeHardx: IResumeHardx = { id: 123 };
        const resumeHardxCollection: IResumeHardx[] = [{ id: 456 }];
        expectedResult = service.addResumeHardxToCollectionIfMissing(resumeHardxCollection, resumeHardx);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeHardx);
      });

      it('should add only unique ResumeHardx to an array', () => {
        const resumeHardxArray: IResumeHardx[] = [{ id: 123 }, { id: 456 }, { id: 44712 }];
        const resumeHardxCollection: IResumeHardx[] = [{ id: 123 }];
        expectedResult = service.addResumeHardxToCollectionIfMissing(resumeHardxCollection, ...resumeHardxArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const resumeHardx: IResumeHardx = { id: 123 };
        const resumeHardx2: IResumeHardx = { id: 456 };
        expectedResult = service.addResumeHardxToCollectionIfMissing([], resumeHardx, resumeHardx2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeHardx);
        expect(expectedResult).toContain(resumeHardx2);
      });

      it('should accept null and undefined values', () => {
        const resumeHardx: IResumeHardx = { id: 123 };
        expectedResult = service.addResumeHardxToCollectionIfMissing([], null, resumeHardx, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeHardx);
      });

      it('should return initial array if no ResumeHardx is added', () => {
        const resumeHardxCollection: IResumeHardx[] = [{ id: 123 }];
        expectedResult = service.addResumeHardxToCollectionIfMissing(resumeHardxCollection, undefined, null);
        expect(expectedResult).toEqual(resumeHardxCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
