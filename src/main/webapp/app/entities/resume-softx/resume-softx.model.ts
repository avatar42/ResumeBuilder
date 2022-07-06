import { IResumeJobs } from 'app/entities/resume-jobs/resume-jobs.model';
import { IResumeSoftware } from 'app/entities/resume-software/resume-software.model';

export interface IResumeSoftx {
  id?: number;
  jobIds?: IResumeJobs[] | null;
  softwareIds?: IResumeSoftware[] | null;
}

export class ResumeSoftx implements IResumeSoftx {
  constructor(public id?: number, public jobIds?: IResumeJobs[] | null, public softwareIds?: IResumeSoftware[] | null) {}
}

export function getResumeSoftxIdentifier(resumeSoftx: IResumeSoftx): number | undefined {
  return resumeSoftx.id;
}
