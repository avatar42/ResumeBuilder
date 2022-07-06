import dayjs from 'dayjs/esm';
import { IResumeHardx } from 'app/entities/resume-hardx/resume-hardx.model';
import { IResumeSoftx } from 'app/entities/resume-softx/resume-softx.model';
import { IResumeRespon } from 'app/entities/resume-respon/resume-respon.model';

export interface IResumeJobs {
  id?: number;
  startDate?: dayjs.Dayjs;
  endDate?: dayjs.Dayjs | null;
  company?: string;
  workStreet?: string | null;
  location?: string;
  workZip?: string | null;
  title?: string;
  months?: number | null;
  contract?: number;
  agency?: string | null;
  mgr?: string;
  mgrPhone?: string | null;
  pay?: string | null;
  corpAddr?: string | null;
  corpPhone?: string | null;
  agencyAddr?: string;
  agencyPhone?: string | null;
  resumeHardx?: IResumeHardx | null;
  resumeSoftx?: IResumeSoftx | null;
  resumeRespon?: IResumeRespon | null;
}

export class ResumeJobs implements IResumeJobs {
  constructor(
    public id?: number,
    public startDate?: dayjs.Dayjs,
    public endDate?: dayjs.Dayjs | null,
    public company?: string,
    public workStreet?: string | null,
    public location?: string,
    public workZip?: string | null,
    public title?: string,
    public months?: number | null,
    public contract?: number,
    public agency?: string | null,
    public mgr?: string,
    public mgrPhone?: string | null,
    public pay?: string | null,
    public corpAddr?: string | null,
    public corpPhone?: string | null,
    public agencyAddr?: string,
    public agencyPhone?: string | null,
    public resumeHardx?: IResumeHardx | null,
    public resumeSoftx?: IResumeSoftx | null,
    public resumeRespon?: IResumeRespon | null
  ) {}
}

export function getResumeJobsIdentifier(resumeJobs: IResumeJobs): number | undefined {
  return resumeJobs.id;
}
