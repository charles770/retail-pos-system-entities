import { Moment } from 'moment';
import { IPedido } from 'app/shared/model/pedido.model';

export interface ICliente {
  id?: number;
  nombreCliente?: string;
  identificacion?: string;
  edad?: number;
  fechaIngreso?: Moment;
  pedidos?: IPedido[];
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public nombreCliente?: string,
    public identificacion?: string,
    public edad?: number,
    public fechaIngreso?: Moment,
    public pedidos?: IPedido[]
  ) {}
}
