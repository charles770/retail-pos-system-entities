import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPedido, Pedido } from 'app/shared/model/pedido.model';
import { PedidoService } from './pedido.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IEmpleado } from 'app/shared/model/empleado.model';
import { EmpleadoService } from 'app/entities/empleado';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';

@Component({
  selector: 'jhi-pedido-update',
  templateUrl: './pedido-update.component.html'
})
export class PedidoUpdateComponent implements OnInit {
  isSaving: boolean;

  clientes: ICliente[];

  empleados: IEmpleado[];

  productos: IProducto[];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    codigo: [],
    subtotal: [],
    descuento: [],
    impuestos: [],
    total: [],
    fecha: [],
    cliente: [],
    empleado: [],
    productos: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pedidoService: PedidoService,
    protected clienteService: ClienteService,
    protected empleadoService: EmpleadoService,
    protected productoService: ProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pedido }) => {
      this.updateForm(pedido);
    });
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.empleadoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmpleado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmpleado[]>) => response.body)
      )
      .subscribe((res: IEmpleado[]) => (this.empleados = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pedido: IPedido) {
    this.editForm.patchValue({
      id: pedido.id,
      codigo: pedido.codigo,
      subtotal: pedido.subtotal,
      descuento: pedido.descuento,
      impuestos: pedido.impuestos,
      total: pedido.total,
      fecha: pedido.fecha,
      cliente: pedido.cliente,
      empleado: pedido.empleado,
      productos: pedido.productos
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pedido = this.createFromForm();
    if (pedido.id !== undefined) {
      this.subscribeToSaveResponse(this.pedidoService.update(pedido));
    } else {
      this.subscribeToSaveResponse(this.pedidoService.create(pedido));
    }
  }

  private createFromForm(): IPedido {
    return {
      ...new Pedido(),
      id: this.editForm.get(['id']).value,
      codigo: this.editForm.get(['codigo']).value,
      subtotal: this.editForm.get(['subtotal']).value,
      descuento: this.editForm.get(['descuento']).value,
      impuestos: this.editForm.get(['impuestos']).value,
      total: this.editForm.get(['total']).value,
      fecha: this.editForm.get(['fecha']).value,
      cliente: this.editForm.get(['cliente']).value,
      empleado: this.editForm.get(['empleado']).value,
      productos: this.editForm.get(['productos']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPedido>>) {
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

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }

  trackEmpleadoById(index: number, item: IEmpleado) {
    return item.id;
  }

  trackProductoById(index: number, item: IProducto) {
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
