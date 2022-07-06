import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IResumeJobs, ResumeJobs } from '../resume-jobs.model';
import { ResumeJobsService } from '../service/resume-jobs.service';

import { ResumeJobsRoutingResolveService } from './resume-jobs-routing-resolve.service';

describe('ResumeJobs routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ResumeJobsRoutingResolveService;
  let service: ResumeJobsService;
  let resultResumeJobs: IResumeJobs | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(ResumeJobsRoutingResolveService);
    service = TestBed.inject(ResumeJobsService);
    resultResumeJobs = undefined;
  });

  describe('resolve', () => {
    it('should return IResumeJobs returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeJobs = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResumeJobs).toEqual({ id: 123 });
    });

    it('should return new IResumeJobs if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeJobs = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultResumeJobs).toEqual(new ResumeJobs());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ResumeJobs })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeJobs = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResumeJobs).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
