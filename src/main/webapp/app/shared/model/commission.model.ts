import { ICommissionRelation } from 'app/shared/model/commission-relation.model';
import { ICouncil } from 'app/shared/model/council.model';

export interface ICommission {
  id?: string;
  name?: string;
  desc?: string;
  commissionRelations?: ICommissionRelation[];
  council?: ICouncil;
}

export class Commission implements ICommission {
  constructor(
    public id?: string,
    public name?: string,
    public desc?: string,
    public commissionRelations?: ICommissionRelation[],
    public council?: ICouncil
  ) {}
}
