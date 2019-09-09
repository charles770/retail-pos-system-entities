import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPermiso, Permiso } from 'app/shared/model/permiso.model';
import { PermisoService } from './permiso.service';

@Component({
  selector: 'jhi-permiso-update',
  templateUrl: './permiso-update.component.html'
})
export class PermisoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombrePermiso: [],
    activo: []
  });

  constructor(protected permisoService: PermisoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ permiso }) => {
      this.updateForm(permiso);
    });
  }

  updateForm(permiso: IPermiso) {
    this.editForm.patchValue({
      id: permiso.id,
      nombrePermiso: permiso.nombrePermiso,
      activo: permiso.activo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const permiso = this.createFromForm();
    if (permiso.id !== undefined) {
      this.subscribeToSaveResponse(this.permisoService.update(permiso));
    } else {
      this.subscribeToSaveResponse(this.permisoService.create(permiso));
    }
  }

  private createFromForm(): IPermiso {
    return {
      ...new Permiso(),
      id: this.editForm.get(['id']).value,
      nombrePermiso: this.editForm.get(['nombrePermiso']).value,
      activo: this.editForm.get(['activo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPermiso>>) {
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
