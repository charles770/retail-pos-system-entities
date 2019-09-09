import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RetailPosSystemSharedModule } from 'app/shared';
import {
  EmpleadoComponent,
  EmpleadoDetailComponent,
  EmpleadoUpdateComponent,
  EmpleadoDeletePopupComponent,
  EmpleadoDeleteDialogComponent,
  empleadoRoute,
  empleadoPopupRoute
} from './';

const ENTITY_STATES = [...empleadoRoute, ...empleadoPopupRoute];

@NgModule({
  imports: [RetailPosSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EmpleadoComponent,
    EmpleadoDetailComponent,
    EmpleadoUpdateComponent,
    EmpleadoDeleteDialogComponent,
    EmpleadoDeletePopupComponent
  ],
  entryComponents: [EmpleadoComponent, EmpleadoUpdateComponent, EmpleadoDeleteDialogComponent, EmpleadoDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RetailPosSystemEmpleadoModule {}
