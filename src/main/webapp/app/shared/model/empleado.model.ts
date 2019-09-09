import { IPedido } from 'app/shared/model/pedido.model';
import { IPerfil } from 'app/shared/model/perfil.model';

export interface IEmpleado {
  id?: number;
  nombreEmpleado?: string;
  usuario?: string;
  contrasena?: string;
  telefono?: string;
  pedidos?: IPedido[];
  perfil?: IPerfil;
}

export class Empleado implements IEmpleado {
  constructor(
    public id?: number,
    public nombreEmpleado?: string,
    public usuario?: string,
    public contrasena?: string,
    public telefono?: string,
    public pedidos?: IPedido[],
    public perfil?: IPerfil
  ) {}
}
