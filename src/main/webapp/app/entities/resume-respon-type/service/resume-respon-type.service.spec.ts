import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IResumeResponType, ResumeResponType } from '../resume-respon-type.model';

import { ResumeResponTypeService } from './resume-respon-type.service';

describe('ResumeResponType Service', () => {
  let service: ResumeResponTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IResumeResponType;
  let expectedResult: IResumeResponType | IResumeResponType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResumeResponTypeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      description: 'AAAAAAA',
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

    it('should create a ResumeResponType', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ResumeResponType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResumeResponType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          description: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResumeResponType', () => {
      const patchObject = Object.assign({}, new ResumeResponType());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResumeResponType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          description: 'BBBBBB',
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

    it('should delete a ResumeResponType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addResumeResponTypeToCollectionIfMissing', () => {
      it('should add a ResumeResponType to an empty array', () => {
        const resumeResponType: IResumeResponType = { id: 123 };
        expectedResult = service.addResumeResponTypeToCollectionIfMissing([], resumeResponType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeResponType);
      });

      it('should not add a ResumeResponType to an array that contains it', () => {
        const resumeResponType: IResumeResponType = { id: 123 };
        const resumeResponTypeCollection: IResumeResponType[] = [
          {
            ...resumeResponType,
          },
          { id: 456 },
        ];
        expectedResult = service.addResumeResponTypeToCollectionIfMissing(resumeResponTypeCollection, resumeResponType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResumeResponType to an array that doesn't contain it", () => {
        const resumeResponType: IResumeResponType = { id: 123 };
        const resumeResponTypeCollection: IResumeResponType[] = [{ id: 456 }];
        expectedResult = service.addResumeResponTypeToCollectionIfMissing(resumeResponTypeCollection, resumeResponType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeResponType);
      });

      it('should add only unique ResumeResponType to an array', () => {
        const resumeResponTypeArray: IResumeResponType[] = [{ id: 123 }, { id: 456 }, { id: 14076 }];
        const resumeResponTypeCollection: IResumeResponType[] = [{ id: 123 }];
        expectedResult = service.addResumeResponTypeToCollectionIfMissing(resumeResponTypeCollection, ...resumeResponTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const resumeResponType: IResumeResponType = { id: 123 };
        const resumeResponType2: IResumeResponType = { id: 456 };
        expectedResult = service.addResumeResponTypeToCollectionIfMissing([], resumeResponType, resumeResponType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeResponType);
        expect(expectedResult).toContain(resumeResponType2);
      });

      it('should accept null and undefined values', () => {
        const resumeResponType: IResumeResponType = { id: 123 };
        expectedResult = service.addResumeResponTypeToCollectionIfMissing([], null, resumeResponType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeResponType);
      });

      it('should return initial array if no ResumeResponType is added', () => {
        const resumeResponTypeCollection: IResumeResponType[] = [{ id: 123 }];
        expectedResult = service.addResumeResponTypeToCollectionIfMissing(resumeResponTypeCollection, undefined, null);
        expect(expectedResult).toEqual(resumeResponTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
