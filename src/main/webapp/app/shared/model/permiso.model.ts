export interface IPermiso {
  id?: number;
  nombrePermiso?: string;
  activo?: boolean;
}

export class Permiso implements IPermiso {
  constructor(public id?: number, public nombrePermiso?: string, public activo?: boolean) {
    this.activo = this.activo || false;
  }
}
