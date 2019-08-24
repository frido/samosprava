export interface IDecision {
  id?: string;
  status?: string;
  description?: string;
}

export class Decision implements IDecision {
  constructor(public id?: string, public status?: string, public description?: string) {}
}
