import { IPerson } from 'app/shared/model/person.model';
import { ICouncil } from 'app/shared/model/council.model';
import { IMeeting } from 'app/shared/model/meeting.model';
import { IDecision } from './decision.model';

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
  number?: string;
  type?: ResolutionType;
  title?: string;
  description?: string;
  voteFor?: number;
  voteAgainst?: number;
  presented?: number;
  source?: string;
  creators?: IPerson[];
  voteFors?: IPerson[];
  voteAgainsts?: IPerson[];
  council?: ICouncil;
  meeting?: IMeeting;
  decisions?: IDecision[];
}

export class Resolution implements IResolution {
  constructor(
    public id?: string,
    public number?: string,
    public type?: ResolutionType,
    public title?: string,
    public description?: string,
    public voteFor?: number,
    public voteAgainst?: number,
    public presented?: number,
    public source?: string,
    public creators?: IPerson[],
    public voteFors?: IPerson[],
    public voteAgainsts?: IPerson[],
    public council?: ICouncil,
    public meeting?: IMeeting,
    public decisions?: IDecision[]
  ) {}
}
