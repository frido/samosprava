import { Moment } from 'moment';

export interface IMeeting {
  id?: string;
  key?: number;
  councilKey?: number;
  date?: Moment;
  place?: string;
}

export class Meeting implements IMeeting {
  constructor(public id?: string, public key?: number, public councilKey?: number, public date?: Moment, public place?: string) {}
}
