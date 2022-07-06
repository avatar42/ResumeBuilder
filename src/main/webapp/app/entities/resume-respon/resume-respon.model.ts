import { IResumeJobs } from 'app/entities/resume-jobs/resume-jobs.model';

export interface IResumeRespon {
  id?: number;
  jobId?: number;
  responOrder?: number;
  responShow?: number;
  jobIds?: IResumeJobs[] | null;
}

export class ResumeRespon implements IResumeRespon {
  constructor(
    public id?: number,
    public jobId?: number,
    public responOrder?: number,
    public responShow?: number,
    public jobIds?: IResumeJobs[] | null
  ) {}
}

export function getResumeResponIdentifier(resumeRespon: IResumeRespon): number | undefined {
  return resumeRespon.id;
}
