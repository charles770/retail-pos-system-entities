import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPerfil, Perfil } from 'app/shared/model/perfil.model';
import { PerfilService } from './perfil.service';
import { IPermiso } from 'app/shared/model/permiso.model';
import { PermisoService } from 'app/entities/permiso';

@Component({
  selector: 'jhi-perfil-update',
  templateUrl: './perfil-update.component.html'
})
export class PerfilUpdateComponent implements OnInit {
  isSaving: boolean;

  permisos: IPermiso[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    descripcion: [],
    permisos: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected perfilService: PerfilService,
    protected permisoService: PermisoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ perfil }) => {
      this.updateForm(perfil);
    });
    this.permisoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPermiso[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPermiso[]>) => response.body)
      )
      .subscribe((res: IPermiso[]) => (this.permisos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(perfil: IPerfil) {
    this.editForm.patchValue({
      id: perfil.id,
      nombre: perfil.nombre,
      descripcion: perfil.descripcion,
      permisos: perfil.permisos
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const perfil = this.createFromForm();
    if (perfil.id !== undefined) {
      this.subscribeToSaveResponse(this.perfilService.update(perfil));
    } else {
      this.subscribeToSaveResponse(this.perfilService.create(perfil));
    }
  }

  private createFromForm(): IPerfil {
    return {
      ...new Perfil(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      permisos: this.editForm.get(['permisos']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfil>>) {
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

  trackPermisoById(index: number, item: IPermiso) {
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
