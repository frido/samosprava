import { IDeputyRelation } from 'app/shared/model/deputy-relation.model';
import { IElection } from 'app/shared/model/election.model';
import { IResolution } from 'app/shared/model/resolution.model';
import { ICouncilRelation } from 'app/shared/model/council-relation.model';
import { IMeeting } from 'app/shared/model/meeting.model';
import { ICommission } from 'app/shared/model/commission.model';
import { IDepartment } from 'app/shared/model/department.model';

export interface ICouncil {
  id?: string;
  key?: string;
  name?: string;
  deputyTitle?: string;
  mayorTitle?: string;
  officeTitle?: string;
  fbTitle?: string;
  fbLink?: string;
  deputyRels?: IDeputyRelation[];
  elections?: IElection[];
  resolutions?: IResolution[];
  councilRels?: ICouncilRelation[];
  meetings?: IMeeting[];
  commissions?: ICommission[];
  departments?: IDepartment[];
}

export class Council implements ICouncil {
  constructor(
    public id?: string,
    public key?: string,
    public name?: string,
    public deputyTitle?: string,
    public mayorTitle?: string,
    public officeTitle?: string,
    public fbTitle?: string,
    public fbLink?: string,
    public deputyRels?: IDeputyRelation[],
    public elections?: IElection[],
    public resolutions?: IResolution[],
    public councilRels?: ICouncilRelation[],
    public meetings?: IMeeting[],
    public commissions?: ICommission[],
    public departments?: IDepartment[]
  ) {}
}
