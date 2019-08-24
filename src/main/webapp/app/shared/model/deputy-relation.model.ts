import { Moment } from 'moment';
import { IVote } from 'app/shared/model/vote.model';
import { IPerson } from 'app/shared/model/person.model';
import { ICouncil } from 'app/shared/model/council.model';

export interface IDeputyRelation {
  id?: string;
  from?: Moment;
  to?: Moment;
  vote?: IVote;
  person?: IPerson;
  council?: ICouncil;
}

export class DeputyRelation implements IDeputyRelation {
  constructor(
    public id?: string,
    public from?: Moment,
    public to?: Moment,
    public vote?: IVote,
    public person?: IPerson,
    public council?: ICouncil
  ) {}
}
