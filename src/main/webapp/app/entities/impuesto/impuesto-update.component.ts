import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IImpuesto, Impuesto } from 'app/shared/model/impuesto.model';
import { ImpuestoService } from './impuesto.service';

@Component({
  selector: 'jhi-impuesto-update',
  templateUrl: './impuesto-update.component.html'
})
export class ImpuestoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    description: [],
    porcentaje: []
  });

  constructor(protected impuestoService: ImpuestoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ impuesto }) => {
      this.updateForm(impuesto);
    });
  }

  updateForm(impuesto: IImpuesto) {
    this.editForm.patchValue({
      id: impuesto.id,
      nombre: impuesto.nombre,
      description: impuesto.description,
      porcentaje: impuesto.porcentaje
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const impuesto = this.createFromForm();
    if (impuesto.id !== undefined) {
      this.subscribeToSaveResponse(this.impuestoService.update(impuesto));
    } else {
      this.subscribeToSaveResponse(this.impuestoService.create(impuesto));
    }
  }

  private createFromForm(): IImpuesto {
    return {
      ...new Impuesto(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      description: this.editForm.get(['description']).value,
      porcentaje: this.editForm.get(['porcentaje']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImpuesto>>) {
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
