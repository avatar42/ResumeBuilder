import { IResumeHardx } from 'app/entities/resume-hardx/resume-hardx.model';

export interface IResumeHardware {
  id?: number;
  repairCertified?: number;
  showSum?: number;
  hardwareName?: string;
  resumeHardx?: IResumeHardx | null;
}

export class ResumeHardware implements IResumeHardware {
  constructor(
    public id?: number,
    public repairCertified?: number,
    public showSum?: number,
    public hardwareName?: string,
    public resumeHardx?: IResumeHardx | null
  ) {}
}

export function getResumeHardwareIdentifier(resumeHardware: IResumeHardware): number | undefined {
  return resumeHardware.id;
}
