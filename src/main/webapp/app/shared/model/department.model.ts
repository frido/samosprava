import { IDepartmentRelation } from 'app/shared/model/department-relation.model';
import { ICouncil } from 'app/shared/model/council.model';

export interface IDepartment {
  id?: string;
  name?: string;
  departmentRelations?: IDepartmentRelation[];
  council?: ICouncil;
}

export class Department implements IDepartment {
  constructor(public id?: string, public name?: string, public departmentRelations?: IDepartmentRelation[], public council?: ICouncil) {}
}
