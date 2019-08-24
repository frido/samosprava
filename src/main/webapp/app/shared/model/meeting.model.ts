import { Moment } from 'moment';
import { IResolution } from 'app/shared/model/resolution.model';
import { ICouncil } from 'app/shared/model/council.model';

export interface IMeeting {
  id?: string;
  date?: Moment;
  place?: string;
  resolutions?: IResolution[];
  council?: ICouncil;
}

export class Meeting implements IMeeting {
  constructor(
    public id?: string,
    public date?: Moment,
    public place?: string,
    public resolutions?: IResolution[],
    public council?: ICouncil
  ) {}
}
