import { Moment } from 'moment';
import { ICouncil } from 'app/shared/model/council.model';
import { IPerson } from 'app/shared/model/person.model';

export const enum CouncilRelationType {
  CHAIRMAN = 'CHAIRMAN',
  VICE_CHAIRMAN = 'VICE_CHAIRMAN',
  Vice1 = 'Vice1',
  Vice2 = 'Vice2',
  Prednosta = 'Prednosta',
  Kontrolor = 'Kontrolor'
}

export interface ICouncilRelation {
  id?: string;
  from?: Moment;
  to?: Moment;
  type?: CouncilRelationType;
  council?: ICouncil;
  person?: IPerson;
}

export class CouncilRelation implements ICouncilRelation {
  constructor(
    public id?: string,
    public from?: Moment,
    public to?: Moment,
    public type?: CouncilRelationType,
    public council?: ICouncil,
    public person?: IPerson
  ) {}
}
