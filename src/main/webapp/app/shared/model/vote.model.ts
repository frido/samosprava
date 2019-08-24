import { IDeputyRelation } from 'app/shared/model/deputy-relation.model';
import { IElection } from 'app/shared/model/election.model';
import { IPerson } from 'app/shared/model/person.model';

export interface IVote {
  id?: string;
  party?: string;
  votes?: number;
  deputy?: IDeputyRelation;
  election?: IElection;
  person?: IPerson;
}

export class Vote implements IVote {
  constructor(
    public id?: string,
    public party?: string,
    public votes?: number,
    public deputy?: IDeputyRelation,
    public election?: IElection,
    public person?: IPerson
  ) {}
}
