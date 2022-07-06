import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResumeRespon, getResumeResponIdentifier } from '../resume-respon.model';

export type EntityResponseType = HttpResponse<IResumeRespon>;
export type EntityArrayResponseType = HttpResponse<IResumeRespon[]>;

@Injectable({ providedIn: 'root' })
export class ResumeResponService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/resume-respons');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(resumeRespon: IResumeRespon): Observable<EntityResponseType> {
    return this.http.post<IResumeRespon>(this.resourceUrl, resumeRespon, { observe: 'response' });
  }

  update(resumeRespon: IResumeRespon): Observable<EntityResponseType> {
    return this.http.put<IResumeRespon>(`${this.resourceUrl}/${getResumeResponIdentifier(resumeRespon) as number}`, resumeRespon, {
      observe: 'response',
    });
  }

  partialUpdate(resumeRespon: IResumeRespon): Observable<EntityResponseType> {
    return this.http.patch<IResumeRespon>(`${this.resourceUrl}/${getResumeResponIdentifier(resumeRespon) as number}`, resumeRespon, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResumeRespon>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResumeRespon[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addResumeResponToCollectionIfMissing(
    resumeResponCollection: IResumeRespon[],
    ...resumeResponsToCheck: (IResumeRespon | null | undefined)[]
  ): IResumeRespon[] {
    const resumeRespons: IResumeRespon[] = resumeResponsToCheck.filter(isPresent);
    if (resumeRespons.length > 0) {
      const resumeResponCollectionIdentifiers = resumeResponCollection.map(
        resumeResponItem => getResumeResponIdentifier(resumeResponItem)!
      );
      const resumeResponsToAdd = resumeRespons.filter(resumeResponItem => {
        const resumeResponIdentifier = getResumeResponIdentifier(resumeResponItem);
        if (resumeResponIdentifier == null || resumeResponCollectionIdentifiers.includes(resumeResponIdentifier)) {
          return false;
        }
        resumeResponCollectionIdentifiers.push(resumeResponIdentifier);
        return true;
      });
      return [...resumeResponsToAdd, ...resumeResponCollection];
    }
    return resumeResponCollection;
  }
}
