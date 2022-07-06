import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IResumeResponType, ResumeResponType } from '../resume-respon-type.model';
import { ResumeResponTypeService } from '../service/resume-respon-type.service';

import { ResumeResponTypeRoutingResolveService } from './resume-respon-type-routing-resolve.service';

describe('ResumeResponType routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ResumeResponTypeRoutingResolveService;
  let service: ResumeResponTypeService;
  let resultResumeResponType: IResumeResponType | undefined;

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
    routingResolveService = TestBed.inject(ResumeResponTypeRoutingResolveService);
    service = TestBed.inject(ResumeResponTypeService);
    resultResumeResponType = undefined;
  });

  describe('resolve', () => {
    it('should return IResumeResponType returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeResponType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResumeResponType).toEqual({ id: 123 });
    });

    it('should return new IResumeResponType if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeResponType = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultResumeResponType).toEqual(new ResumeResponType());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ResumeResponType })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResumeResponType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResumeResponType).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
