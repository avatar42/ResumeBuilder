import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResumeHardx, getResumeHardxIdentifier } from '../resume-hardx.model';

export type EntityResponseType = HttpResponse<IResumeHardx>;
export type EntityArrayResponseType = HttpResponse<IResumeHardx[]>;

@Injectable({ providedIn: 'root' })
export class ResumeHardxService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/resume-hardxes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(resumeHardx: IResumeHardx): Observable<EntityResponseType> {
    return this.http.post<IResumeHardx>(this.resourceUrl, resumeHardx, { observe: 'response' });
  }

  update(resumeHardx: IResumeHardx): Observable<EntityResponseType> {
    return this.http.put<IResumeHardx>(`${this.resourceUrl}/${getResumeHardxIdentifier(resumeHardx) as number}`, resumeHardx, {
      observe: 'response',
    });
  }

  partialUpdate(resumeHardx: IResumeHardx): Observable<EntityResponseType> {
    return this.http.patch<IResumeHardx>(`${this.resourceUrl}/${getResumeHardxIdentifier(resumeHardx) as number}`, resumeHardx, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResumeHardx>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResumeHardx[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addResumeHardxToCollectionIfMissing(
    resumeHardxCollection: IResumeHardx[],
    ...resumeHardxesToCheck: (IResumeHardx | null | undefined)[]
  ): IResumeHardx[] {
    const resumeHardxes: IResumeHardx[] = resumeHardxesToCheck.filter(isPresent);
    if (resumeHardxes.length > 0) {
      const resumeHardxCollectionIdentifiers = resumeHardxCollection.map(resumeHardxItem => getResumeHardxIdentifier(resumeHardxItem)!);
      const resumeHardxesToAdd = resumeHardxes.filter(resumeHardxItem => {
        const resumeHardxIdentifier = getResumeHardxIdentifier(resumeHardxItem);
        if (resumeHardxIdentifier == null || resumeHardxCollectionIdentifiers.includes(resumeHardxIdentifier)) {
          return false;
        }
        resumeHardxCollectionIdentifiers.push(resumeHardxIdentifier);
        return true;
      });
      return [...resumeHardxesToAdd, ...resumeHardxCollection];
    }
    return resumeHardxCollection;
  }
}
