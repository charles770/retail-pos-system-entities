export interface IProveedor {
  id?: number;
  nombreProveedor?: string;
  identificacion?: string;
  nombreVendedor?: string;
  telefono?: string;
  direccion?: string;
}

export class Proveedor implements IProveedor {
  constructor(
    public id?: number,
    public nombreProveedor?: string,
    public identificacion?: string,
    public nombreVendedor?: string,
    public telefono?: string,
    public direccion?: string
  ) {}
}
