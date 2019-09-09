import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RetailPosSystemSharedModule } from 'app/shared';
import {
  PermisoComponent,
  PermisoDetailComponent,
  PermisoUpdateComponent,
  PermisoDeletePopupComponent,
  PermisoDeleteDialogComponent,
  permisoRoute,
  permisoPopupRoute
} from './';

const ENTITY_STATES = [...permisoRoute, ...permisoPopupRoute];

@NgModule({
  imports: [RetailPosSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PermisoComponent,
    PermisoDetailComponent,
    PermisoUpdateComponent,
    PermisoDeleteDialogComponent,
    PermisoDeletePopupComponent
  ],
  entryComponents: [PermisoComponent, PermisoUpdateComponent, PermisoDeleteDialogComponent, PermisoDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RetailPosSystemPermisoModule {}
