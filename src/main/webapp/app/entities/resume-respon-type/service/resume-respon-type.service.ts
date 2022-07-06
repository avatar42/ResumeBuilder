import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResumeResponType, getResumeResponTypeIdentifier } from '../resume-respon-type.model';

export type EntityResponseType = HttpResponse<IResumeResponType>;
export type EntityArrayResponseType = HttpResponse<IResumeResponType[]>;

@Injectable({ providedIn: 'root' })
export class ResumeResponTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/resume-respon-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(resumeResponType: IResumeResponType): Observable<EntityResponseType> {
    return this.http.post<IResumeResponType>(this.resourceUrl, resumeResponType, { observe: 'response' });
  }

  update(resumeResponType: IResumeResponType): Observable<EntityResponseType> {
    return this.http.put<IResumeResponType>(
      `${this.resourceUrl}/${getResumeResponTypeIdentifier(resumeResponType) as number}`,
      resumeResponType,
      { observe: 'response' }
    );
  }

  partialUpdate(resumeResponType: IResumeResponType): Observable<EntityResponseType> {
    return this.http.patch<IResumeResponType>(
      `${this.resourceUrl}/${getResumeResponTypeIdentifier(resumeResponType) as number}`,
      resumeResponType,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResumeResponType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResumeResponType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addResumeResponTypeToCollectionIfMissing(
    resumeResponTypeCollection: IResumeResponType[],
    ...resumeResponTypesToCheck: (IResumeResponType | null | undefined)[]
  ): IResumeResponType[] {
    const resumeResponTypes: IResumeResponType[] = resumeResponTypesToCheck.filter(isPresent);
    if (resumeResponTypes.length > 0) {
      const resumeResponTypeCollectionIdentifiers = resumeResponTypeCollection.map(
        resumeResponTypeItem => getResumeResponTypeIdentifier(resumeResponTypeItem)!
      );
      const resumeResponTypesToAdd = resumeResponTypes.filter(resumeResponTypeItem => {
        const resumeResponTypeIdentifier = getResumeResponTypeIdentifier(resumeResponTypeItem);
        if (resumeResponTypeIdentifier == null || resumeResponTypeCollectionIdentifiers.includes(resumeResponTypeIdentifier)) {
          return false;
        }
        resumeResponTypeCollectionIdentifiers.push(resumeResponTypeIdentifier);
        return true;
      });
      return [...resumeResponTypesToAdd, ...resumeResponTypeCollection];
    }
    return resumeResponTypeCollection;
  }
}
