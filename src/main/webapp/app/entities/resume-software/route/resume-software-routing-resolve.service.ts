import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResumeSoftware, ResumeSoftware } from '../resume-software.model';
import { ResumeSoftwareService } from '../service/resume-software.service';

@Injectable({ providedIn: 'root' })
export class ResumeSoftwareRoutingResolveService implements Resolve<IResumeSoftware> {
  constructor(protected service: ResumeSoftwareService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResumeSoftware> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((resumeSoftware: HttpResponse<ResumeSoftware>) => {
          if (resumeSoftware.body) {
            return of(resumeSoftware.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResumeSoftware());
  }
}
