import { IMeeting } from 'app/shared/model/meeting.model';

export const enum ResolutionType {
  RENT = 'RENT',
  OTHER = 'OTHER',
  PETITION = 'PETITION',
  INFO = 'INFO',
  REQUEST = 'REQUEST',
  INSPECTION = 'INSPECTION',
  PROJECT = 'PROJECT',
  BUDGET = 'BUDGET',
  REGION = 'REGION',
  TRANSFER = 'TRANSFER',
  RESERVATION = 'RESERVATION',
  VZN = 'VZN',
  CHOICE = 'CHOICE'
}

export interface IResolution {
  id?: string;
  key?: number;
  number?: string;
  type?: ResolutionType;
  councilKey?: number;
  creatorKey?: string;
  title?: string;
  description?: string;
  voteFor?: number;
  voteAgainst?: number;
  presented?: number;
  source?: string;
  meeting?: IMeeting;
}

export class Resolution implements IResolution {
  constructor(
    public id?: string,
    public key?: number,
    public number?: string,
    public type?: ResolutionType,
    public councilKey?: number,
    public creatorKey?: string,
    public title?: string,
    public description?: string,
    public voteFor?: number,
    public voteAgainst?: number,
    public presented?: number,
    public source?: string,
    public meeting?: IMeeting
  ) {}
}
