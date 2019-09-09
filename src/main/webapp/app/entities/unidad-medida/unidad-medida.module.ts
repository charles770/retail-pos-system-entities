import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RetailPosSystemSharedModule } from 'app/shared';
import {
  UnidadMedidaComponent,
  UnidadMedidaDetailComponent,
  UnidadMedidaUpdateComponent,
  UnidadMedidaDeletePopupComponent,
  UnidadMedidaDeleteDialogComponent,
  unidadMedidaRoute,
  unidadMedidaPopupRoute
} from './';

const ENTITY_STATES = [...unidadMedidaRoute, ...unidadMedidaPopupRoute];

@NgModule({
  imports: [RetailPosSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UnidadMedidaComponent,
    UnidadMedidaDetailComponent,
    UnidadMedidaUpdateComponent,
    UnidadMedidaDeleteDialogComponent,
    UnidadMedidaDeletePopupComponent
  ],
  entryComponents: [
    UnidadMedidaComponent,
    UnidadMedidaUpdateComponent,
    UnidadMedidaDeleteDialogComponent,
    UnidadMedidaDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RetailPosSystemUnidadMedidaModule {}
