import { ICouncil } from 'app/shared/model/council.model';

export interface IBudget {
  id?: string;
  council?: ICouncil;
}

export class Budget implements IBudget {
  constructor(public id?: string, public council?: ICouncil) {}
}
