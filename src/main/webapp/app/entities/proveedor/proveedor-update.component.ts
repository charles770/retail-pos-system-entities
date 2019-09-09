import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProveedor, Proveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from './proveedor.service';

@Component({
  selector: 'jhi-proveedor-update',
  templateUrl: './proveedor-update.component.html'
})
export class ProveedorUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombreProveedor: [],
    identificacion: [],
    nombreVendedor: [],
    telefono: [],
    direccion: []
  });

  constructor(protected proveedorService: ProveedorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ proveedor }) => {
      this.updateForm(proveedor);
    });
  }

  updateForm(proveedor: IProveedor) {
    this.editForm.patchValue({
      id: proveedor.id,
      nombreProveedor: proveedor.nombreProveedor,
      identificacion: proveedor.identificacion,
      nombreVendedor: proveedor.nombreVendedor,
      telefono: proveedor.telefono,
      direccion: proveedor.direccion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const proveedor = this.createFromForm();
    if (proveedor.id !== undefined) {
      this.subscribeToSaveResponse(this.proveedorService.update(proveedor));
    } else {
      this.subscribeToSaveResponse(this.proveedorService.create(proveedor));
    }
  }

  private createFromForm(): IProveedor {
    return {
      ...new Proveedor(),
      id: this.editForm.get(['id']).value,
      nombreProveedor: this.editForm.get(['nombreProveedor']).value,
      identificacion: this.editForm.get(['identificacion']).value,
      nombreVendedor: this.editForm.get(['nombreVendedor']).value,
      telefono: this.editForm.get(['telefono']).value,
      direccion: this.editForm.get(['direccion']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProveedor>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
