import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResumeResponType, ResumeResponType } from '../resume-respon-type.model';
import { ResumeResponTypeService } from '../service/resume-respon-type.service';

@Injectable({ providedIn: 'root' })
export class ResumeResponTypeRoutingResolveService implements Resolve<IResumeResponType> {
  constructor(protected service: ResumeResponTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResumeResponType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((resumeResponType: HttpResponse<ResumeResponType>) => {
          if (resumeResponType.body) {
            return of(resumeResponType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResumeResponType());
  }
}
