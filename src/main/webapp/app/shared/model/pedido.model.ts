import { Moment } from 'moment';
import { ICliente } from 'app/shared/model/cliente.model';
import { IEmpleado } from 'app/shared/model/empleado.model';
import { IProducto } from 'app/shared/model/producto.model';

export interface IPedido {
  id?: number;
  codigo?: string;
  subtotal?: number;
  descuento?: number;
  impuestos?: number;
  total?: number;
  fecha?: Moment;
  cliente?: ICliente;
  empleado?: IEmpleado;
  productos?: IProducto[];
}

export class Pedido implements IPedido {
  constructor(
    public id?: number,
    public codigo?: string,
    public subtotal?: number,
    public descuento?: number,
    public impuestos?: number,
    public total?: number,
    public fecha?: Moment,
    public cliente?: ICliente,
    public empleado?: IEmpleado,
    public productos?: IProducto[]
  ) {}
}
