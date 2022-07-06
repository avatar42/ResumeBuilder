import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResumeJobs, ResumeJobs } from '../resume-jobs.model';
import { ResumeJobsService } from '../service/resume-jobs.service';

@Injectable({ providedIn: 'root' })
export class ResumeJobsRoutingResolveService implements Resolve<IResumeJobs> {
  constructor(protected service: ResumeJobsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResumeJobs> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((resumeJobs: HttpResponse<ResumeJobs>) => {
          if (resumeJobs.body) {
            return of(resumeJobs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResumeJobs());
  }
}
