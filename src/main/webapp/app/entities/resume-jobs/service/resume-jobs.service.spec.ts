import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IResumeJobs, ResumeJobs } from '../resume-jobs.model';

import { ResumeJobsService } from './resume-jobs.service';

describe('ResumeJobs Service', () => {
  let service: ResumeJobsService;
  let httpMock: HttpTestingController;
  let elemDefault: IResumeJobs;
  let expectedResult: IResumeJobs | IResumeJobs[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResumeJobsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      startDate: currentDate,
      endDate: currentDate,
      company: 'AAAAAAA',
      workStreet: 'AAAAAAA',
      location: 'AAAAAAA',
      workZip: 'AAAAAAA',
      title: 'AAAAAAA',
      months: 0,
      contract: 0,
      agency: 'AAAAAAA',
      mgr: 'AAAAAAA',
      mgrPhone: 'AAAAAAA',
      pay: 'AAAAAAA',
      corpAddr: 'AAAAAAA',
      corpPhone: 'AAAAAAA',
      agencyAddr: 'AAAAAAA',
      agencyPhone: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ResumeJobs', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
        },
        returnedFromService
      );

      service.create(new ResumeJobs()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResumeJobs', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          company: 'BBBBBB',
          workStreet: 'BBBBBB',
          location: 'BBBBBB',
          workZip: 'BBBBBB',
          title: 'BBBBBB',
          months: 1,
          contract: 1,
          agency: 'BBBBBB',
          mgr: 'BBBBBB',
          mgrPhone: 'BBBBBB',
          pay: 'BBBBBB',
          corpAddr: 'BBBBBB',
          corpPhone: 'BBBBBB',
          agencyAddr: 'BBBBBB',
          agencyPhone: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResumeJobs', () => {
      const patchObject = Object.assign(
        {
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          workStreet: 'BBBBBB',
          location: 'BBBBBB',
          months: 1,
          agency: 'BBBBBB',
          mgr: 'BBBBBB',
          pay: 'BBBBBB',
          agencyAddr: 'BBBBBB',
          agencyPhone: 'BBBBBB',
        },
        new ResumeJobs()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResumeJobs', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          company: 'BBBBBB',
          workStreet: 'BBBBBB',
          location: 'BBBBBB',
          workZip: 'BBBBBB',
          title: 'BBBBBB',
          months: 1,
          contract: 1,
          agency: 'BBBBBB',
          mgr: 'BBBBBB',
          mgrPhone: 'BBBBBB',
          pay: 'BBBBBB',
          corpAddr: 'BBBBBB',
          corpPhone: 'BBBBBB',
          agencyAddr: 'BBBBBB',
          agencyPhone: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ResumeJobs', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addResumeJobsToCollectionIfMissing', () => {
      it('should add a ResumeJobs to an empty array', () => {
        const resumeJobs: IResumeJobs = { id: 123 };
        expectedResult = service.addResumeJobsToCollectionIfMissing([], resumeJobs);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeJobs);
      });

      it('should not add a ResumeJobs to an array that contains it', () => {
        const resumeJobs: IResumeJobs = { id: 123 };
        const resumeJobsCollection: IResumeJobs[] = [
          {
            ...resumeJobs,
          },
          { id: 456 },
        ];
        expectedResult = service.addResumeJobsToCollectionIfMissing(resumeJobsCollection, resumeJobs);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResumeJobs to an array that doesn't contain it", () => {
        const resumeJobs: IResumeJobs = { id: 123 };
        const resumeJobsCollection: IResumeJobs[] = [{ id: 456 }];
        expectedResult = service.addResumeJobsToCollectionIfMissing(resumeJobsCollection, resumeJobs);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeJobs);
      });

      it('should add only unique ResumeJobs to an array', () => {
        const resumeJobsArray: IResumeJobs[] = [{ id: 123 }, { id: 456 }, { id: 67331 }];
        const resumeJobsCollection: IResumeJobs[] = [{ id: 123 }];
        expectedResult = service.addResumeJobsToCollectionIfMissing(resumeJobsCollection, ...resumeJobsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const resumeJobs: IResumeJobs = { id: 123 };
        const resumeJobs2: IResumeJobs = { id: 456 };
        expectedResult = service.addResumeJobsToCollectionIfMissing([], resumeJobs, resumeJobs2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumeJobs);
        expect(expectedResult).toContain(resumeJobs2);
      });

      it('should accept null and undefined values', () => {
        const resumeJobs: IResumeJobs = { id: 123 };
        expectedResult = service.addResumeJobsToCollectionIfMissing([], null, resumeJobs, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumeJobs);
      });

      it('should return initial array if no ResumeJobs is added', () => {
        const resumeJobsCollection: IResumeJobs[] = [{ id: 123 }];
        expectedResult = service.addResumeJobsToCollectionIfMissing(resumeJobsCollection, undefined, null);
        expect(expectedResult).toEqual(resumeJobsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
