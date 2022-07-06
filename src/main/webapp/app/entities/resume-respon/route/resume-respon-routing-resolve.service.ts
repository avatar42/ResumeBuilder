import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResumeRespon, ResumeRespon } from '../resume-respon.model';
import { ResumeResponService } from '../service/resume-respon.service';

@Injectable({ providedIn: 'root' })
export class ResumeResponRoutingResolveService implements Resolve<IResumeRespon> {
  constructor(protected service: ResumeResponService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResumeRespon> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((resumeRespon: HttpResponse<ResumeRespon>) => {
          if (resumeRespon.body) {
            return of(resumeRespon.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResumeRespon());
  }
}
