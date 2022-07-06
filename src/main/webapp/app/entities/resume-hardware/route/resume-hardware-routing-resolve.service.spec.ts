import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IResumeHardware, ResumeHardware } from '../resume-hardware.model';
import { ResumeHardwareService } from '../service/resume-hardware.service';

import { ResumeHardwareRoutingResolveService } from './resume-hardware-routing-resolve.service';

describe('ResumeHardware routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ResumeHardwareRoutingResolveService;
  let service: ResumeHardwareService;
  let resultResumeHardware: IResumeHardware | undefined;

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
    routingResolveService = TestBed.inject(ResumeHardwareRoutingResolveService);
    service = TestBed.inject(ResumeHardwareService);
    resultResumeHardware = undefined;
  });

  describe('resolve', () => {
    it('should return IResumeHardware returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeHardware = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResumeHardware).toEqual({ id: 123 });
    });

    it('should return new IResumeHardware if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeHardware = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultResumeHardware).toEqual(new ResumeHardware());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ResumeHardware })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeHardware = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResumeHardware).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
