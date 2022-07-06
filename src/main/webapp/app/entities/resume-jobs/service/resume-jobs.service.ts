import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResumeJobs, getResumeJobsIdentifier } from '../resume-jobs.model';

export type EntityResponseType = HttpResponse<IResumeJobs>;
export type EntityArrayResponseType = HttpResponse<IResumeJobs[]>;

@Injectable({ providedIn: 'root' })
export class ResumeJobsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/resume-jobs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(resumeJobs: IResumeJobs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resumeJobs);
    return this.http
      .post<IResumeJobs>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(resumeJobs: IResumeJobs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resumeJobs);
    return this.http
      .put<IResumeJobs>(`${this.resourceUrl}/${getResumeJobsIdentifier(resumeJobs) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(resumeJobs: IResumeJobs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resumeJobs);
    return this.http
      .patch<IResumeJobs>(`${this.resourceUrl}/${getResumeJobsIdentifier(resumeJobs) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IResumeJobs>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IResumeJobs[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addResumeJobsToCollectionIfMissing(
    resumeJobsCollection: IResumeJobs[],
    ...resumeJobsToCheck: (IResumeJobs | null | undefined)[]
  ): IResumeJobs[] {
    const resumeJobs: IResumeJobs[] = resumeJobsToCheck.filter(isPresent);
    if (resumeJobs.length > 0) {
      const resumeJobsCollectionIdentifiers = resumeJobsCollection.map(resumeJobsItem => getResumeJobsIdentifier(resumeJobsItem)!);
      const resumeJobsToAdd = resumeJobs.filter(resumeJobsItem => {
        const resumeJobsIdentifier = getResumeJobsIdentifier(resumeJobsItem);
        if (resumeJobsIdentifier == null || resumeJobsCollectionIdentifiers.includes(resumeJobsIdentifier)) {
          return false;
        }
        resumeJobsCollectionIdentifiers.push(resumeJobsIdentifier);
        return true;
      });
      return [...resumeJobsToAdd, ...resumeJobsCollection];
    }
    return resumeJobsCollection;
  }

  protected convertDateFromClient(resumeJobs: IResumeJobs): IResumeJobs {
    return Object.assign({}, resumeJobs, {
      startDate: resumeJobs.startDate?.isValid() ? resumeJobs.startDate.format(DATE_FORMAT) : undefined,
      endDate: resumeJobs.endDate?.isValid() ? resumeJobs.endDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? dayjs(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? dayjs(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((resumeJobs: IResumeJobs) => {
        resumeJobs.startDate = resumeJobs.startDate ? dayjs(resumeJobs.startDate) : undefined;
        resumeJobs.endDate = resumeJobs.endDate ? dayjs(resumeJobs.endDate) : undefined;
      });
    }
    return res;
  }
}
