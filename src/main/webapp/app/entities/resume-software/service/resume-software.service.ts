import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResumeSoftware, getResumeSoftwareIdentifier } from '../resume-software.model';

export type EntityResponseType = HttpResponse<IResumeSoftware>;
export type EntityArrayResponseType = HttpResponse<IResumeSoftware[]>;

@Injectable({ providedIn: 'root' })
export class ResumeSoftwareService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/resume-softwares');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(resumeSoftware: IResumeSoftware): Observable<EntityResponseType> {
    return this.http.post<IResumeSoftware>(this.resourceUrl, resumeSoftware, { observe: 'response' });
  }

  update(resumeSoftware: IResumeSoftware): Observable<EntityResponseType> {
    return this.http.put<IResumeSoftware>(`${this.resourceUrl}/${getResumeSoftwareIdentifier(resumeSoftware) as number}`, resumeSoftware, {
      observe: 'response',
    });
  }

  partialUpdate(resumeSoftware: IResumeSoftware): Observable<EntityResponseType> {
    return this.http.patch<IResumeSoftware>(
      `${this.resourceUrl}/${getResumeSoftwareIdentifier(resumeSoftware) as number}`,
      resumeSoftware,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResumeSoftware>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResumeSoftware[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addResumeSoftwareToCollectionIfMissing(
    resumeSoftwareCollection: IResumeSoftware[],
    ...resumeSoftwaresToCheck: (IResumeSoftware | null | undefined)[]
  ): IResumeSoftware[] {
    const resumeSoftwares: IResumeSoftware[] = resumeSoftwaresToCheck.filter(isPresent);
    if (resumeSoftwares.length > 0) {
      const resumeSoftwareCollectionIdentifiers = resumeSoftwareCollection.map(
        resumeSoftwareItem => getResumeSoftwareIdentifier(resumeSoftwareItem)!
      );
      const resumeSoftwaresToAdd = resumeSoftwares.filter(resumeSoftwareItem => {
        const resumeSoftwareIdentifier = getResumeSoftwareIdentifier(resumeSoftwareItem);
        if (resumeSoftwareIdentifier == null || resumeSoftwareCollectionIdentifiers.includes(resumeSoftwareIdentifier)) {
          return false;
        }
        resumeSoftwareCollectionIdentifiers.push(resumeSoftwareIdentifier);
        return true;
      });
      return [...resumeSoftwaresToAdd, ...resumeSoftwareCollection];
    }
    return resumeSoftwareCollection;
  }
}
