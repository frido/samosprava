import { Moment } from 'moment';
import { IDepartment } from 'app/shared/model/department.model';
import { IPerson } from 'app/shared/model/person.model';

export const enum DepartmentRelationType {
  SCHOOL_MEMBER = 'SCHOOL_MEMBER',
  DIRECTOR = 'DIRECTOR',
  OFFICIAL = 'OFFICIAL',
  HEAD_OFFICIAL = 'HEAD_OFFICIAL',
  SUPERVISOR = 'SUPERVISOR',
  VICE_SUPERVISOR = 'VICE_SUPERVISOR',
  HEAD_SUPERVISOR = 'HEAD_SUPERVISOR'
}

export interface IDepartmentRelation {
  id?: string;
  from?: Moment;
  to?: Moment;
  type?: DepartmentRelationType;
  department?: IDepartment;
  person?: IPerson;
}

export class DepartmentRelation implements IDepartmentRelation {
  constructor(
    public id?: string,
    public from?: Moment,
    public to?: Moment,
    public type?: DepartmentRelationType,
    public department?: IDepartment,
    public person?: IPerson
  ) {}
}
