export interface IBudget {
  id?: string;
}

export class Budget implements IBudget {
  constructor(public id?: string) {}
}
