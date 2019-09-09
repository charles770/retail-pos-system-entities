import { ICategoria } from 'app/shared/model/categoria.model';

export interface ICategoria {
  id?: number;
  nombre?: string;
  abreviatura?: string;
  foto?: string;
  colorFondo?: number;
  categoria?: ICategoria;
  categorias?: ICategoria[];
}

export class Categoria implements ICategoria {
  constructor(
    public id?: number,
    public nombre?: string,
    public abreviatura?: string,
    public foto?: string,
    public colorFondo?: number,
    public categoria?: ICategoria,
    public categorias?: ICategoria[]
  ) {}
}
