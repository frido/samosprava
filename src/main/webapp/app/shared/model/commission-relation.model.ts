import { Moment } from 'moment';
import { ICommission } from 'app/shared/model/commission.model';
import { IPerson } from 'app/shared/model/person.model';

export const enum CommissionRelationType {
  HEAD = 'HEAD',
  MEMBER = 'MEMBER'
}

export interface ICommissionRelation {
  id?: string;
  from?: Moment;
  to?: Moment;
  type?: CommissionRelationType;
  commission?: ICommission;
  person?: IPerson;
}

export class CommissionRelation implements ICommissionRelation {
  constructor(
    public id?: string,
    public from?: Moment,
    public to?: Moment,
    public type?: CommissionRelationType,
    public commission?: ICommission,
    public person?: IPerson
  ) {}
}
