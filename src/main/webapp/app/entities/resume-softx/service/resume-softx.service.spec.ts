import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IResumeSoftx, ResumeSoftx } from '../resume-softx.model';

import { ResumeSoftxService } from './resume-softx.service';

describe('ResumeSoftx Service', () => {
  let service: ResumeSoftxService;
  let httpMock: HttpTestingController;
  let elemDefault: IResumeSoftx;
  let expectedResult: IResumeSoftx | IResumeSoftx[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResumeSoftxService);
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

    it('should create a ResumeSoftx', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ResumeSoftx()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResumeSoftx', () => {
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

    it('should partial update a ResumeSoftx', () => {
      const patchObject = Object.assign({}, new ResumeSoftx());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResumeSoftx', () => {
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

    it('should delete a ResumeSoftx', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addResumeSoftxToCollectionIfMissing', () => {
      it('should add a ResumeSoftx to an empty array', () => {
        const resumeSoftx: IResumeSoftx = { id: 123 };
        expectedResult = service.addResumeSoftxToCollectionIfMissing([], resumeSoftx);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeSoftx);
      });

      it('should not add a ResumeSoftx to an array that contains it', () => {
        const resumeSoftx: IResumeSoftx = { id: 123 };
        const resumeSoftxCollection: IResumeSoftx[] = [
          {
            ...resumeSoftx,
          },
          { id: 456 },
        ];
        expectedResult = service.addResumeSoftxToCollectionIfMissing(resumeSoftxCollection, resumeSoftx);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResumeSoftx to an array that doesn't contain it", () => {
        const resumeSoftx: IResumeSoftx = { id: 123 };
        const resumeSoftxCollection: IResumeSoftx[] = [{ id: 456 }];
        expectedResult = service.addResumeSoftxToCollectionIfMissing(resumeSoftxCollection, resumeSoftx);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeSoftx);
      });

      it('should add only unique ResumeSoftx to an array', () => {
        const resumeSoftxArray: IResumeSoftx[] = [{ id: 123 }, { id: 456 }, { id: 4371 }];
        const resumeSoftxCollection: IResumeSoftx[] = [{ id: 123 }];
        expectedResult = service.addResumeSoftxToCollectionIfMissing(resumeSoftxCollection, ...resumeSoftxArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const resumeSoftx: IResumeSoftx = { id: 123 };
        const resumeSoftx2: IResumeSoftx = { id: 456 };
        expectedResult = service.addResumeSoftxToCollectionIfMissing([], resumeSoftx, resumeSoftx2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeSoftx);
        expect(expectedResult).toContain(resumeSoftx2);
      });

      it('should accept null and undefined values', () => {
        const resumeSoftx: IResumeSoftx = { id: 123 };
        expectedResult = service.addResumeSoftxToCollectionIfMissing([], null, resumeSoftx, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeSoftx);
      });

      it('should return initial array if no ResumeSoftx is added', () => {
        const resumeSoftxCollection: IResumeSoftx[] = [{ id: 123 }];
        expectedResult = service.addResumeSoftxToCollectionIfMissing(resumeSoftxCollection, undefined, null);
        expect(expectedResult).toEqual(resumeSoftxCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
