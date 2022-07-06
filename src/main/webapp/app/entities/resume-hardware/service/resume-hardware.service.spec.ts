import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IResumeHardware, ResumeHardware } from '../resume-hardware.model';

import { ResumeHardwareService } from './resume-hardware.service';

describe('ResumeHardware Service', () => {
  let service: ResumeHardwareService;
  let httpMock: HttpTestingController;
  let elemDefault: IResumeHardware;
  let expectedResult: IResumeHardware | IResumeHardware[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResumeHardwareService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      repairCertified: 0,
      showSum: 0,
      hardwareName: 'AAAAAAA',
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

    it('should create a ResumeHardware', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ResumeHardware()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResumeHardware', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          repairCertified: 1,
          showSum: 1,
          hardwareName: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResumeHardware', () => {
      const patchObject = Object.assign(
        {
          repairCertified: 1,
          showSum: 1,
          hardwareName: 'BBBBBB',
        },
        new ResumeHardware()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResumeHardware', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          repairCertified: 1,
          showSum: 1,
          hardwareName: 'BBBBBB',
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

    it('should delete a ResumeHardware', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addResumeHardwareToCollectionIfMissing', () => {
      it('should add a ResumeHardware to an empty array', () => {
        const resumeHardware: IResumeHardware = { id: 123 };
        expectedResult = service.addResumeHardwareToCollectionIfMissing([], resumeHardware);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeHardware);
      });

      it('should not add a ResumeHardware to an array that contains it', () => {
        const resumeHardware: IResumeHardware = { id: 123 };
        const resumeHardwareCollection: IResumeHardware[] = [
          {
            ...resumeHardware,
          },
          { id: 456 },
        ];
        expectedResult = service.addResumeHardwareToCollectionIfMissing(resumeHardwareCollection, resumeHardware);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResumeHardware to an array that doesn't contain it", () => {
        const resumeHardware: IResumeHardware = { id: 123 };
        const resumeHardwareCollection: IResumeHardware[] = [{ id: 456 }];
        expectedResult = service.addResumeHardwareToCollectionIfMissing(resumeHardwareCollection, resumeHardware);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeHardware);
      });

      it('should add only unique ResumeHardware to an array', () => {
        const resumeHardwareArray: IResumeHardware[] = [{ id: 123 }, { id: 456 }, { id: 52309 }];
        const resumeHardwareCollection: IResumeHardware[] = [{ id: 123 }];
        expectedResult = service.addResumeHardwareToCollectionIfMissing(resumeHardwareCollection, ...resumeHardwareArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const resumeHardware: IResumeHardware = { id: 123 };
        const resumeHardware2: IResumeHardware = { id: 456 };
        expectedResult = service.addResumeHardwareToCollectionIfMissing([], resumeHardware, resumeHardware2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeHardware);
        expect(expectedResult).toContain(resumeHardware2);
      });

      it('should accept null and undefined values', () => {
        const resumeHardware: IResumeHardware = { id: 123 };
        expectedResult = service.addResumeHardwareToCollectionIfMissing([], null, resumeHardware, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeHardware);
      });

      it('should return initial array if no ResumeHardware is added', () => {
        const resumeHardwareCollection: IResumeHardware[] = [{ id: 123 }];
        expectedResult = service.addResumeHardwareToCollectionIfMissing(resumeHardwareCollection, undefined, null);
        expect(expectedResult).toEqual(resumeHardwareCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
