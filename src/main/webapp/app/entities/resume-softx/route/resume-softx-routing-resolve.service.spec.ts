import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IResumeSoftx, ResumeSoftx } from '../resume-softx.model';
import { ResumeSoftxService } from '../service/resume-softx.service';

import { ResumeSoftxRoutingResolveService } from './resume-softx-routing-resolve.service';

describe('ResumeSoftx routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ResumeSoftxRoutingResolveService;
  let service: ResumeSoftxService;
  let resultResumeSoftx: IResumeSoftx | undefined;

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
    routingResolveService = TestBed.inject(ResumeSoftxRoutingResolveService);
    service = TestBed.inject(ResumeSoftxService);
    resultResumeSoftx = undefined;
  });

  describe('resolve', () => {
    it('should return IResumeSoftx returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeSoftx = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResumeSoftx).toEqual({ id: 123 });
    });

    it('should return new IResumeSoftx if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeSoftx = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultResumeSoftx).toEqual(new ResumeSoftx());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ResumeSoftx })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeSoftx = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResumeSoftx).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
