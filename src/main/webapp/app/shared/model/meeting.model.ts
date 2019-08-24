import { Moment } from 'moment';
import { IResolution } from 'app/shared/model/resolution.model';

export interface IMeeting {
  id?: string;
  key?: number;
  councilKey?: number;
  date?: Moment;
  place?: string;
  resolutions?: IResolution[];
}

export class Meeting implements IMeeting {
  constructor(
    public id?: string,
    public key?: number,
    public councilKey?: number,
    public date?: Moment,
    public place?: string,
    public resolutions?: IResolution[]
  ) {}
}
