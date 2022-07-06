import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IResumeRespon, ResumeRespon } from '../resume-respon.model';

import { ResumeResponService } from './resume-respon.service';

describe('ResumeRespon Service', () => {
  let service: ResumeResponService;
  let httpMock: HttpTestingController;
  let elemDefault: IResumeRespon;
  let expectedResult: IResumeRespon | IResumeRespon[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResumeResponService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      jobId: 0,
      responOrder: 0,
      responShow: 0,
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

    it('should create a ResumeRespon', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ResumeRespon()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResumeRespon', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          jobId: 1,
          responOrder: 1,
          responShow: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResumeRespon', () => {
      const patchObject = Object.assign(
        {
          responOrder: 1,
          responShow: 1,
        },
        new ResumeRespon()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResumeRespon', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          jobId: 1,
          responOrder: 1,
          responShow: 1,
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

    it('should delete a ResumeRespon', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addResumeResponToCollectionIfMissing', () => {
      it('should add a ResumeRespon to an empty array', () => {
        const resumeRespon: IResumeRespon = { id: 123 };
        expectedResult = service.addResumeResponToCollectionIfMissing([], resumeRespon);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeRespon);
      });

      it('should not add a ResumeRespon to an array that contains it', () => {
        const resumeRespon: IResumeRespon = { id: 123 };
        const resumeResponCollection: IResumeRespon[] = [
          {
            ...resumeRespon,
          },
          { id: 456 },
        ];
        expectedResult = service.addResumeResponToCollectionIfMissing(resumeResponCollection, resumeRespon);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResumeRespon to an array that doesn't contain it", () => {
        const resumeRespon: IResumeRespon = { id: 123 };
        const resumeResponCollection: IResumeRespon[] = [{ id: 456 }];
        expectedResult = service.addResumeResponToCollectionIfMissing(resumeResponCollection, resumeRespon);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeRespon);
      });

      it('should add only unique ResumeRespon to an array', () => {
        const resumeResponArray: IResumeRespon[] = [{ id: 123 }, { id: 456 }, { id: 98996 }];
        const resumeResponCollection: IResumeRespon[] = [{ id: 123 }];
        expectedResult = service.addResumeResponToCollectionIfMissing(resumeResponCollection, ...resumeResponArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const resumeRespon: IResumeRespon = { id: 123 };
        const resumeRespon2: IResumeRespon = { id: 456 };
        expectedResult = service.addResumeResponToCollectionIfMissing([], resumeRespon, resumeRespon2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeRespon);
        expect(expectedResult).toContain(resumeRespon2);
      });

      it('should accept null and undefined values', () => {
        const resumeRespon: IResumeRespon = { id: 123 };
        expectedResult = service.addResumeResponToCollectionIfMissing([], null, resumeRespon, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeRespon);
      });

      it('should return initial array if no ResumeRespon is added', () => {
        const resumeResponCollection: IResumeRespon[] = [{ id: 123 }];
        expectedResult = service.addResumeResponToCollectionIfMissing(resumeResponCollection, undefined, null);
        expect(expectedResult).toEqual(resumeResponCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
