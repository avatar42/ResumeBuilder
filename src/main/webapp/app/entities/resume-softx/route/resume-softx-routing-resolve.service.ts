import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResumeSoftx, ResumeSoftx } from '../resume-softx.model';
import { ResumeSoftxService } from '../service/resume-softx.service';

@Injectable({ providedIn: 'root' })
export class ResumeSoftxRoutingResolveService implements Resolve<IResumeSoftx> {
  constructor(protected service: ResumeSoftxService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResumeSoftx> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((resumeSoftx: HttpResponse<ResumeSoftx>) => {
          if (resumeSoftx.body) {
            return of(resumeSoftx.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResumeSoftx());
  }
}
