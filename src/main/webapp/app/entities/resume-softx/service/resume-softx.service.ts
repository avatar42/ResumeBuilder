import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResumeSoftx, getResumeSoftxIdentifier } from '../resume-softx.model';

export type EntityResponseType = HttpResponse<IResumeSoftx>;
export type EntityArrayResponseType = HttpResponse<IResumeSoftx[]>;

@Injectable({ providedIn: 'root' })
export class ResumeSoftxService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/resume-softxes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(resumeSoftx: IResumeSoftx): Observable<EntityResponseType> {
    return this.http.post<IResumeSoftx>(this.resourceUrl, resumeSoftx, { observe: 'response' });
  }

  update(resumeSoftx: IResumeSoftx): Observable<EntityResponseType> {
    return this.http.put<IResumeSoftx>(`${this.resourceUrl}/${getResumeSoftxIdentifier(resumeSoftx) as number}`, resumeSoftx, {
      observe: 'response',
    });
  }

  partialUpdate(resumeSoftx: IResumeSoftx): Observable<EntityResponseType> {
    return this.http.patch<IResumeSoftx>(`${this.resourceUrl}/${getResumeSoftxIdentifier(resumeSoftx) as number}`, resumeSoftx, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResumeSoftx>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResumeSoftx[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addResumeSoftxToCollectionIfMissing(
    resumeSoftxCollection: IResumeSoftx[],
    ...resumeSoftxesToCheck: (IResumeSoftx | null | undefined)[]
  ): IResumeSoftx[] {
    const resumeSoftxes: IResumeSoftx[] = resumeSoftxesToCheck.filter(isPresent);
    if (resumeSoftxes.length > 0) {
      const resumeSoftxCollectionIdentifiers = resumeSoftxCollection.map(resumeSoftxItem => getResumeSoftxIdentifier(resumeSoftxItem)!);
      const resumeSoftxesToAdd = resumeSoftxes.filter(resumeSoftxItem => {
        const resumeSoftxIdentifier = getResumeSoftxIdentifier(resumeSoftxItem);
        if (resumeSoftxIdentifier == null || resumeSoftxCollectionIdentifiers.includes(resumeSoftxIdentifier)) {
          return false;
        }
        resumeSoftxCollectionIdentifiers.push(resumeSoftxIdentifier);
        return true;
      });
      return [...resumeSoftxesToAdd, ...resumeSoftxCollection];
    }
    return resumeSoftxCollection;
  }
}
