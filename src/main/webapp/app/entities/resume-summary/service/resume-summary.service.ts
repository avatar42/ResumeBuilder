import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResumeSummary, getResumeSummaryIdentifier } from '../resume-summary.model';

export type EntityResponseType = HttpResponse<IResumeSummary>;
export type EntityArrayResponseType = HttpResponse<IResumeSummary[]>;

@Injectable({ providedIn: 'root' })
export class ResumeSummaryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/resume-summaries');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(resumeSummary: IResumeSummary): Observable<EntityResponseType> {
    return this.http.post<IResumeSummary>(this.resourceUrl, resumeSummary, { observe: 'response' });
  }

  update(resumeSummary: IResumeSummary): Observable<EntityResponseType> {
    return this.http.put<IResumeSummary>(`${this.resourceUrl}/${getResumeSummaryIdentifier(resumeSummary) as number}`, resumeSummary, {
      observe: 'response',
    });
  }

  partialUpdate(resumeSummary: IResumeSummary): Observable<EntityResponseType> {
    return this.http.patch<IResumeSummary>(`${this.resourceUrl}/${getResumeSummaryIdentifier(resumeSummary) as number}`, resumeSummary, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResumeSummary>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResumeSummary[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addResumeSummaryToCollectionIfMissing(
    resumeSummaryCollection: IResumeSummary[],
    ...resumeSummariesToCheck: (IResumeSummary | null | undefined)[]
  ): IResumeSummary[] {
    const resumeSummaries: IResumeSummary[] = resumeSummariesToCheck.filter(isPresent);
    if (resumeSummaries.length > 0) {
      const resumeSummaryCollectionIdentifiers = resumeSummaryCollection.map(
        resumeSummaryItem => getResumeSummaryIdentifier(resumeSummaryItem)!
      );
      const resumeSummariesToAdd = resumeSummaries.filter(resumeSummaryItem => {
        const resumeSummaryIdentifier = getResumeSummaryIdentifier(resumeSummaryItem);
        if (resumeSummaryIdentifier == null || resumeSummaryCollectionIdentifiers.includes(resumeSummaryIdentifier)) {
          return false;
        }
        resumeSummaryCollectionIdentifiers.push(resumeSummaryIdentifier);
        return true;
      });
      return [...resumeSummariesToAdd, ...resumeSummaryCollection];
    }
    return resumeSummaryCollection;
  }
}
