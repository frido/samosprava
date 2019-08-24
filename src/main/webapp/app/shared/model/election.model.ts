import { Moment } from 'moment';
import { IVote } from 'app/shared/model/vote.model';
import { ICouncil } from 'app/shared/model/council.model';

export const enum ElectionType {
  DEPUTY = 'DEPUTY',
  MAYOR = 'MAYOR'
}

export interface IElection {
  id?: string;
  name?: string;
  date?: Moment;
  type?: ElectionType;
  votes?: IVote[];
  council?: ICouncil;
}

export class Election implements IElection {
  constructor(
    public id?: string,
    public name?: string,
    public date?: Moment,
    public type?: ElectionType,
    public votes?: IVote[],
    public council?: ICouncil
  ) {}
}
