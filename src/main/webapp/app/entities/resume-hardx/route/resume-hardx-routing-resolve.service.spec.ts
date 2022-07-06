import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IResumeHardx, ResumeHardx } from '../resume-hardx.model';
import { ResumeHardxService } from '../service/resume-hardx.service';

import { ResumeHardxRoutingResolveService } from './resume-hardx-routing-resolve.service';

describe('ResumeHardx routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ResumeHardxRoutingResolveService;
  let service: ResumeHardxService;
  let resultResumeHardx: IResumeHardx | undefined;

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
    routingResolveService = TestBed.inject(ResumeHardxRoutingResolveService);
    service = TestBed.inject(ResumeHardxService);
    resultResumeHardx = undefined;
  });

  describe('resolve', () => {
    it('should return IResumeHardx returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeHardx = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResumeHardx).toEqual({ id: 123 });
    });

    it('should return new IResumeHardx if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeHardx = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultResumeHardx).toEqual(new ResumeHardx());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ResumeHardx })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeHardx = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResumeHardx).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
