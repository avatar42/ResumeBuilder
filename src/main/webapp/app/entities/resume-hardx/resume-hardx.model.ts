import { IResumeHardware } from 'app/entities/resume-hardware/resume-hardware.model';
import { IResumeJobs } from 'app/entities/resume-jobs/resume-jobs.model';

export interface IResumeHardx {
  id?: number;
  hardwareIds?: IResumeHardware[] | null;
  jobIds?: IResumeJobs[] | null;
}

export class ResumeHardx implements IResumeHardx {
  constructor(public id?: number, public hardwareIds?: IResumeHardware[] | null, public jobIds?: IResumeJobs[] | null) {}
}

export function getResumeHardxIdentifier(resumeHardx: IResumeHardx): number | undefined {
  return resumeHardx.id;
}
