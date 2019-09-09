import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { ProductoService } from './producto.service';
import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';
import { UnidadMedidaService } from 'app/entities/unidad-medida';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria';
import { IImpuesto } from 'app/shared/model/impuesto.model';
import { ImpuestoService } from 'app/entities/impuesto';

@Component({
  selector: 'jhi-producto-update',
  templateUrl: './producto-update.component.html'
})
export class ProductoUpdateComponent implements OnInit {
  isSaving: boolean;

  unidadmedidas: IUnidadMedida[];

  proveedors: IProveedor[];

  categorias: ICategoria[];

  impuestos: IImpuesto[];

  editForm = this.fb.group({
    id: [],
    descripcion: [],
    codigo: [],
    foto: [],
    cantidad: [],
    precioCompra: [],
    precioVenta: [],
    minimasUnidades: [],
    unidadesPedido: [],
    codum: [],
    proveedor: [],
    codcategoria: [],
    impuestos: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productoService: ProductoService,
    protected unidadMedidaService: UnidadMedidaService,
    protected proveedorService: ProveedorService,
    protected categoriaService: CategoriaService,
    protected impuestoService: ImpuestoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.updateForm(producto);
    });
    this.unidadMedidaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUnidadMedida[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUnidadMedida[]>) => response.body)
      )
      .subscribe((res: IUnidadMedida[]) => (this.unidadmedidas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.proveedorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProveedor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProveedor[]>) => response.body)
      )
      .subscribe((res: IProveedor[]) => (this.proveedors = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.categoriaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICategoria[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategoria[]>) => response.body)
      )
      .subscribe((res: ICategoria[]) => (this.categorias = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.impuestoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IImpuesto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IImpuesto[]>) => response.body)
      )
      .subscribe((res: IImpuesto[]) => (this.impuestos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(producto: IProducto) {
    this.editForm.patchValue({
      id: producto.id,
      descripcion: producto.descripcion,
      codigo: producto.codigo,
      foto: producto.foto,
      cantidad: producto.cantidad,
      precioCompra: producto.precioCompra,
      precioVenta: producto.precioVenta,
      minimasUnidades: producto.minimasUnidades,
      unidadesPedido: producto.unidadesPedido,
      codum: producto.codum,
      proveedor: producto.proveedor,
      codcategoria: producto.codcategoria,
      impuestos: producto.impuestos
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const producto = this.createFromForm();
    if (producto.id !== undefined) {
      this.subscribeToSaveResponse(this.productoService.update(producto));
    } else {
      this.subscribeToSaveResponse(this.productoService.create(producto));
    }
  }

  private createFromForm(): IProducto {
    return {
      ...new Producto(),
      id: this.editForm.get(['id']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      codigo: this.editForm.get(['codigo']).value,
      foto: this.editForm.get(['foto']).value,
      cantidad: this.editForm.get(['cantidad']).value,
      precioCompra: this.editForm.get(['precioCompra']).value,
      precioVenta: this.editForm.get(['precioVenta']).value,
      minimasUnidades: this.editForm.get(['minimasUnidades']).value,
      unidadesPedido: this.editForm.get(['unidadesPedido']).value,
      codum: this.editForm.get(['codum']).value,
      proveedor: this.editForm.get(['proveedor']).value,
      codcategoria: this.editForm.get(['codcategoria']).value,
      impuestos: this.editForm.get(['impuestos']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUnidadMedidaById(index: number, item: IUnidadMedida) {
    return item.id;
  }

  trackProveedorById(index: number, item: IProveedor) {
    return item.id;
  }

  trackCategoriaById(index: number, item: ICategoria) {
    return item.id;
  }

  trackImpuestoById(index: number, item: IImpuesto) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
