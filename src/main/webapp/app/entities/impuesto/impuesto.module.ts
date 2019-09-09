import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RetailPosSystemSharedModule } from 'app/shared';
import {
  ImpuestoComponent,
  ImpuestoDetailComponent,
  ImpuestoUpdateComponent,
  ImpuestoDeletePopupComponent,
  ImpuestoDeleteDialogComponent,
  impuestoRoute,
  impuestoPopupRoute
} from './';

const ENTITY_STATES = [...impuestoRoute, ...impuestoPopupRoute];

@NgModule({
  imports: [RetailPosSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ImpuestoComponent,
    ImpuestoDetailComponent,
    ImpuestoUpdateComponent,
    ImpuestoDeleteDialogComponent,
    ImpuestoDeletePopupComponent
  ],
  entryComponents: [ImpuestoComponent, ImpuestoUpdateComponent, ImpuestoDeleteDialogComponent, ImpuestoDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RetailPosSystemImpuestoModule {}
