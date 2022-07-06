import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IResumeRespon, ResumeRespon } from '../resume-respon.model';
import { ResumeResponService } from '../service/resume-respon.service';

import { ResumeResponRoutingResolveService } from './resume-respon-routing-resolve.service';

describe('ResumeRespon routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ResumeResponRoutingResolveService;
  let service: ResumeResponService;
  let resultResumeRespon: IResumeRespon | undefined;

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
    routingResolveService = TestBed.inject(ResumeResponRoutingResolveService);
    service = TestBed.inject(ResumeResponService);
    resultResumeRespon = undefined;
  });

  describe('resolve', () => {
    it('should return IResumeRespon returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeRespon = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResumeRespon).toEqual({ id: 123 });
    });

    it('should return new IResumeRespon if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeRespon = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultResumeRespon).toEqual(new ResumeRespon());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ResumeRespon })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeRespon = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResumeRespon).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
