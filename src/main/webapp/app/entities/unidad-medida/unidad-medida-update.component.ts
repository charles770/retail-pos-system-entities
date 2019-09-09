import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IUnidadMedida, UnidadMedida } from 'app/shared/model/unidad-medida.model';
import { UnidadMedidaService } from './unidad-medida.service';

@Component({
  selector: 'jhi-unidad-medida-update',
  templateUrl: './unidad-medida-update.component.html'
})
export class UnidadMedidaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombreUnidadMedida: [],
    abreviatura: []
  });

  constructor(protected unidadMedidaService: UnidadMedidaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ unidadMedida }) => {
      this.updateForm(unidadMedida);
    });
  }

  updateForm(unidadMedida: IUnidadMedida) {
    this.editForm.patchValue({
      id: unidadMedida.id,
      nombreUnidadMedida: unidadMedida.nombreUnidadMedida,
      abreviatura: unidadMedida.abreviatura
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const unidadMedida = this.createFromForm();
    if (unidadMedida.id !== undefined) {
      this.subscribeToSaveResponse(this.unidadMedidaService.update(unidadMedida));
    } else {
      this.subscribeToSaveResponse(this.unidadMedidaService.create(unidadMedida));
    }
  }

  private createFromForm(): IUnidadMedida {
    return {
      ...new UnidadMedida(),
      id: this.editForm.get(['id']).value,
      nombreUnidadMedida: this.editForm.get(['nombreUnidadMedida']).value,
      abreviatura: this.editForm.get(['abreviatura']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnidadMedida>>) {
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
