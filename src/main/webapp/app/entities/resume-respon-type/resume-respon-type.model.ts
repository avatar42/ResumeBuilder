export interface IResumeResponType {
  id?: number;
  description?: string | null;
}

export class ResumeResponType implements IResumeResponType {
  constructor(public id?: number, public description?: string | null) {}
}

export function getResumeResponTypeIdentifier(resumeResponType: IResumeResponType): number | undefined {
  return resumeResponType.id;
}
