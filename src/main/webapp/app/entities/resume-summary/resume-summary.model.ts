import { IResumeSoftware } from 'app/entities/resume-software/resume-software.model';

export interface IResumeSummary {
  id?: number;
  summaryName?: string;
  summaryOrder?: number;
  resumeSoftware?: IResumeSoftware | null;
}

export class ResumeSummary implements IResumeSummary {
  constructor(
    public id?: number,
    public summaryName?: string,
    public summaryOrder?: number,
    public resumeSoftware?: IResumeSoftware | null
  ) {}
}

export function getResumeSummaryIdentifier(resumeSummary: IResumeSummary): number | undefined {
  return resumeSummary.id;
}
