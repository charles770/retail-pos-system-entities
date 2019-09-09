import { IPermiso } from 'app/shared/model/permiso.model';

export interface IPerfil {
  id?: number;
  nombre?: string;
  descripcion?: string;
  permisos?: IPermiso[];
}

export class Perfil implements IPerfil {
  constructor(public id?: number, public nombre?: string, public descripcion?: string, public permisos?: IPermiso[]) {}
}
