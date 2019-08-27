import { IDeputyRelation } from 'app/shared/model/deputy-relation.model';
import { IVote } from 'app/shared/model/vote.model';
import { ICouncilRelation } from 'app/shared/model/council-relation.model';
import { IDepartmentRelation } from 'app/shared/model/department-relation.model';
import { ICommissionRelation } from 'app/shared/model/commission-relation.model';
import { IResolution } from 'app/shared/model/resolution.model';

export interface IPerson {
  id?: string;
  name?: string;
  prefix?: string;
  suffix?: string;
  club?: string;
  facebook?: string;
  deputyRelations?: IDeputyRelation[];
  votes?: IVote[];
  councilRelations?: ICouncilRelation[];
  departmentRelations?: IDepartmentRelation[];
  commissionRelations?: ICommissionRelation[];
  creatorsOfs?: IResolution[];
  voteForOfs?: IResolution[];
  voteAgainstOfs?: IResolution[];
}

export class Person implements IPerson {
  constructor(
    public id?: string,
    public name?: string,
    public prefix?: string,
    public suffix?: string,
    public club?: string,
    public facebook?: string,
    public deputyRelations?: IDeputyRelation[],
    public votes?: IVote[],
    public councilRelations?: ICouncilRelation[],
    public departmentRelations?: IDepartmentRelation[],
    public commissionRelations?: ICommissionRelation[],
    public creatorsOfs?: IResolution[],
    public voteForOfs?: IResolution[],
    public voteAgainstOfs?: IResolution[]
  ) {}
}
