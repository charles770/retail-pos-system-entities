import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ICategoria } from 'app/shared/model/categoria.model';
import { IImpuesto } from 'app/shared/model/impuesto.model';

export interface IProducto {
  id?: number;
  descripcion?: string;
  codigo?: string;
  foto?: string;
  cantidad?: number;
  precioCompra?: number;
  precioVenta?: number;
  minimasUnidades?: number;
  unidadesPedido?: number;
  codum?: IUnidadMedida;
  proveedor?: IProveedor;
  codcategoria?: ICategoria;
  impuestos?: IImpuesto[];
}

export class Producto implements IProducto {
  constructor(
    public id?: number,
    public descripcion?: string,
    public codigo?: string,
    public foto?: string,
    public cantidad?: number,
    public precioCompra?: number,
    public precioVenta?: number,
    public minimasUnidades?: number,
    public unidadesPedido?: number,
    public codum?: IUnidadMedida,
    public proveedor?: IProveedor,
    public codcategoria?: ICategoria,
    public impuestos?: IImpuesto[]
  ) {}
}
