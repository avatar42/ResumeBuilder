import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IResumeSummary, ResumeSummary } from '../resume-summary.model';
import { ResumeSummaryService } from '../service/resume-summary.service';

import { ResumeSummaryRoutingResolveService } from './resume-summary-routing-resolve.service';

describe('ResumeSummary routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ResumeSummaryRoutingResolveService;
  let service: ResumeSummaryService;
  let resultResumeSummary: IResumeSummary | undefined;

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
    routingResolveService = TestBed.inject(ResumeSummaryRoutingResolveService);
    service = TestBed.inject(ResumeSummaryService);
    resultResumeSummary = undefined;
  });

  describe('resolve', () => {
    it('should return IResumeSummary returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeSummary = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResumeSummary).toEqual({ id: 123 });
    });

    it('should return new IResumeSummary if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeSummary = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultResumeSummary).toEqual(new ResumeSummary());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ResumeSummary })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeSummary = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResumeSummary).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
