import { IResumeSummary } from 'app/entities/resume-summary/resume-summary.model';
import { IResumeSoftx } from 'app/entities/resume-softx/resume-softx.model';

export interface IResumeSoftware {
  id?: number;
  softwareName?: string;
  softwareVer?: string;
  softwareCatId?: number;
  summarryCatId?: number;
  softwareCatIds?: IResumeSoftware[] | null;
  summarryCatIds?: IResumeSummary[] | null;
  resumeSoftx?: IResumeSoftx | null;
  resumeSoftware?: IResumeSoftware | null;
}

export class ResumeSoftware implements IResumeSoftware {
  constructor(
    public id?: number,
    public softwareName?: string,
    public softwareVer?: string,
    public softwareCatId?: number,
    public summarryCatId?: number,
    public softwareCatIds?: IResumeSoftware[] | null,
    public summarryCatIds?: IResumeSummary[] | null,
    public resumeSoftx?: IResumeSoftx | null,
    public resumeSoftware?: IResumeSoftware | null
  ) {}
}

export function getResumeSoftwareIdentifier(resumeSoftware: IResumeSoftware): number | undefined {
  return resumeSoftware.id;
}
