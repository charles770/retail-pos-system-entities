export interface IImpuesto {
  id?: number;
  nombre?: string;
  description?: string;
  porcentaje?: number;
}

export class Impuesto implements IImpuesto {
  constructor(public id?: number, public nombre?: string, public description?: string, public porcentaje?: number) {}
}
