import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResumeSummary, ResumeSummary } from '../resume-summary.model';
import { ResumeSummaryService } from '../service/resume-summary.service';

@Injectable({ providedIn: 'root' })
export class ResumeSummaryRoutingResolveService implements Resolve<IResumeSummary> {
  constructor(protected service: ResumeSummaryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResumeSummary> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((resumeSummary: HttpResponse<ResumeSummary>) => {
          if (resumeSummary.body) {
            return of(resumeSummary.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResumeSummary());
  }
}
