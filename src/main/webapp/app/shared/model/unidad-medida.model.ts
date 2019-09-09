export interface IUnidadMedida {
  id?: number;
  nombreUnidadMedida?: string;
  abreviatura?: string;
}

export class UnidadMedida implements IUnidadMedida {
  constructor(public id?: number, public nombreUnidadMedida?: string, public abreviatura?: string) {}
}
