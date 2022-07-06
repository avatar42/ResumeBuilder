import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IResumeSoftware, ResumeSoftware } from '../resume-software.model';

import { ResumeSoftwareService } from './resume-software.service';

describe('ResumeSoftware Service', () => {
  let service: ResumeSoftwareService;
  let httpMock: HttpTestingController;
  let elemDefault: IResumeSoftware;
  let expectedResult: IResumeSoftware | IResumeSoftware[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResumeSoftwareService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      softwareName: 'AAAAAAA',
      softwareVer: 'AAAAAAA',
      softwareCatId: 0,
      summarryCatId: 0,
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

    it('should create a ResumeSoftware', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ResumeSoftware()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResumeSoftware', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          softwareName: 'BBBBBB',
          softwareVer: 'BBBBBB',
          softwareCatId: 1,
          summarryCatId: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResumeSoftware', () => {
      const patchObject = Object.assign(
        {
          softwareName: 'BBBBBB',
          softwareVer: 'BBBBBB',
          softwareCatId: 1,
          summarryCatId: 1,
        },
        new ResumeSoftware()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResumeSoftware', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          softwareName: 'BBBBBB',
          softwareVer: 'BBBBBB',
          softwareCatId: 1,
          summarryCatId: 1,
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

    it('should delete a ResumeSoftware', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addResumeSoftwareToCollectionIfMissing', () => {
      it('should add a ResumeSoftware to an empty array', () => {
        const resumeSoftware: IResumeSoftware = { id: 123 };
        expectedResult = service.addResumeSoftwareToCollectionIfMissing([], resumeSoftware);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeSoftware);
      });

      it('should not add a ResumeSoftware to an array that contains it', () => {
        const resumeSoftware: IResumeSoftware = { id: 123 };
        const resumeSoftwareCollection: IResumeSoftware[] = [
          {
            ...resumeSoftware,
          },
          { id: 456 },
        ];
        expectedResult = service.addResumeSoftwareToCollectionIfMissing(resumeSoftwareCollection, resumeSoftware);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResumeSoftware to an array that doesn't contain it", () => {
        const resumeSoftware: IResumeSoftware = { id: 123 };
        const resumeSoftwareCollection: IResumeSoftware[] = [{ id: 456 }];
        expectedResult = service.addResumeSoftwareToCollectionIfMissing(resumeSoftwareCollection, resumeSoftware);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeSoftware);
      });

      it('should add only unique ResumeSoftware to an array', () => {
        const resumeSoftwareArray: IResumeSoftware[] = [{ id: 123 }, { id: 456 }, { id: 16953 }];
        const resumeSoftwareCollection: IResumeSoftware[] = [{ id: 123 }];
        expectedResult = service.addResumeSoftwareToCollectionIfMissing(resumeSoftwareCollection, ...resumeSoftwareArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const resumeSoftware: IResumeSoftware = { id: 123 };
        const resumeSoftware2: IResumeSoftware = { id: 456 };
        expectedResult = service.addResumeSoftwareToCollectionIfMissing([], resumeSoftware, resumeSoftware2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeSoftware);
        expect(expectedResult).toContain(resumeSoftware2);
      });

      it('should accept null and undefined values', () => {
        const resumeSoftware: IResumeSoftware = { id: 123 };
        expectedResult = service.addResumeSoftwareToCollectionIfMissing([], null, resumeSoftware, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeSoftware);
      });

      it('should return initial array if no ResumeSoftware is added', () => {
        const resumeSoftwareCollection: IResumeSoftware[] = [{ id: 123 }];
        expectedResult = service.addResumeSoftwareToCollectionIfMissing(resumeSoftwareCollection, undefined, null);
        expect(expectedResult).toEqual(resumeSoftwareCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
