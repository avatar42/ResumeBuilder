import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResumeHardware, getResumeHardwareIdentifier } from '../resume-hardware.model';

export type EntityResponseType = HttpResponse<IResumeHardware>;
export type EntityArrayResponseType = HttpResponse<IResumeHardware[]>;

@Injectable({ providedIn: 'root' })
export class ResumeHardwareService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/resume-hardwares');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(resumeHardware: IResumeHardware): Observable<EntityResponseType> {
    return this.http.post<IResumeHardware>(this.resourceUrl, resumeHardware, { observe: 'response' });
  }

  update(resumeHardware: IResumeHardware): Observable<EntityResponseType> {
    return this.http.put<IResumeHardware>(`${this.resourceUrl}/${getResumeHardwareIdentifier(resumeHardware) as number}`, resumeHardware, {
      observe: 'response',
    });
  }

  partialUpdate(resumeHardware: IResumeHardware): Observable<EntityResponseType> {
    return this.http.patch<IResumeHardware>(
      `${this.resourceUrl}/${getResumeHardwareIdentifier(resumeHardware) as number}`,
      resumeHardware,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResumeHardware>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResumeHardware[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addResumeHardwareToCollectionIfMissing(
    resumeHardwareCollection: IResumeHardware[],
    ...resumeHardwaresToCheck: (IResumeHardware | null | undefined)[]
  ): IResumeHardware[] {
    const resumeHardwares: IResumeHardware[] = resumeHardwaresToCheck.filter(isPresent);
    if (resumeHardwares.length > 0) {
      const resumeHardwareCollectionIdentifiers = resumeHardwareCollection.map(
        resumeHardwareItem => getResumeHardwareIdentifier(resumeHardwareItem)!
      );
      const resumeHardwaresToAdd = resumeHardwares.filter(resumeHardwareItem => {
        const resumeHardwareIdentifier = getResumeHardwareIdentifier(resumeHardwareItem);
        if (resumeHardwareIdentifier == null || resumeHardwareCollectionIdentifiers.includes(resumeHardwareIdentifier)) {
          return false;
        }
        resumeHardwareCollectionIdentifiers.push(resumeHardwareIdentifier);
        return true;
      });
      return [...resumeHardwaresToAdd, ...resumeHardwareCollection];
    }
    return resumeHardwareCollection;
  }
}
