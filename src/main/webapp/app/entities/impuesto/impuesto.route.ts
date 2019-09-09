import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Impuesto } from 'app/shared/model/impuesto.model';
import { ImpuestoService } from './impuesto.service';
import { ImpuestoComponent } from './impuesto.component';
import { ImpuestoDetailComponent } from './impuesto-detail.component';
import { ImpuestoUpdateComponent } from './impuesto-update.component';
import { ImpuestoDeletePopupComponent } from './impuesto-delete-dialog.component';
import { IImpuesto } from 'app/shared/model/impuesto.model';

@Injectable({ providedIn: 'root' })
export class ImpuestoResolve implements Resolve<IImpuesto> {
  constructor(private service: ImpuestoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IImpuesto> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Impuesto>) => response.ok),
        map((impuesto: HttpResponse<Impuesto>) => impuesto.body)
      );
    }
    return of(new Impuesto());
  }
}

export const impuestoRoute: Routes = [
  {
    path: '',
    component: ImpuestoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Impuestos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ImpuestoDetailComponent,
    resolve: {
      impuesto: ImpuestoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Impuestos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ImpuestoUpdateComponent,
    resolve: {
      impuesto: ImpuestoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Impuestos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ImpuestoUpdateComponent,
    resolve: {
      impuesto: ImpuestoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Impuestos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const impuestoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ImpuestoDeletePopupComponent,
    resolve: {
      impuesto: ImpuestoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Impuestos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
