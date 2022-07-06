import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IResumeSummary, ResumeSummary } from '../resume-summary.model';

import { ResumeSummaryService } from './resume-summary.service';

describe('ResumeSummary Service', () => {
  let service: ResumeSummaryService;
  let httpMock: HttpTestingController;
  let elemDefault: IResumeSummary;
  let expectedResult: IResumeSummary | IResumeSummary[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResumeSummaryService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      summaryName: 'AAAAAAA',
      summaryOrder: 0,
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

    it('should create a ResumeSummary', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ResumeSummary()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResumeSummary', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          summaryName: 'BBBBBB',
          summaryOrder: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResumeSummary', () => {
      const patchObject = Object.assign(
        {
          summaryName: 'BBBBBB',
          summaryOrder: 1,
        },
        new ResumeSummary()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResumeSummary', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          summaryName: 'BBBBBB',
          summaryOrder: 1,
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

    it('should delete a ResumeSummary', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addResumeSummaryToCollectionIfMissing', () => {
      it('should add a ResumeSummary to an empty array', () => {
        const resumeSummary: IResumeSummary = { id: 123 };
        expectedResult = service.addResumeSummaryToCollectionIfMissing([], resumeSummary);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeSummary);
      });

      it('should not add a ResumeSummary to an array that contains it', () => {
        const resumeSummary: IResumeSummary = { id: 123 };
        const resumeSummaryCollection: IResumeSummary[] = [
          {
            ...resumeSummary,
          },
          { id: 456 },
        ];
        expectedResult = service.addResumeSummaryToCollectionIfMissing(resumeSummaryCollection, resumeSummary);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResumeSummary to an array that doesn't contain it", () => {
        const resumeSummary: IResumeSummary = { id: 123 };
        const resumeSummaryCollection: IResumeSummary[] = [{ id: 456 }];
        expectedResult = service.addResumeSummaryToCollectionIfMissing(resumeSummaryCollection, resumeSummary);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeSummary);
      });

      it('should add only unique ResumeSummary to an array', () => {
        const resumeSummaryArray: IResumeSummary[] = [{ id: 123 }, { id: 456 }, { id: 22114 }];
        const resumeSummaryCollection: IResumeSummary[] = [{ id: 123 }];
        expectedResult = service.addResumeSummaryToCollectionIfMissing(resumeSummaryCollection, ...resumeSummaryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const resumeSummary: IResumeSummary = { id: 123 };
        const resumeSummary2: IResumeSummary = { id: 456 };
        expectedResult = service.addResumeSummaryToCollectionIfMissing([], resumeSummary, resumeSummary2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeSummary);
        expect(expectedResult).toContain(resumeSummary2);
      });

      it('should accept null and undefined values', () => {
        const resumeSummary: IResumeSummary = { id: 123 };
        expectedResult = service.addResumeSummaryToCollectionIfMissing([], null, resumeSummary, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeSummary);
      });

      it('should return initial array if no ResumeSummary is added', () => {
        const resumeSummaryCollection: IResumeSummary[] = [{ id: 123 }];
        expectedResult = service.addResumeSummaryToCollectionIfMissing(resumeSummaryCollection, undefined, null);
        expect(expectedResult).toEqual(resumeSummaryCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
