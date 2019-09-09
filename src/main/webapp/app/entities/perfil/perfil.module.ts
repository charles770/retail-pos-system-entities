import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RetailPosSystemSharedModule } from 'app/shared';
import {
  PerfilComponent,
  PerfilDetailComponent,
  PerfilUpdateComponent,
  PerfilDeletePopupComponent,
  PerfilDeleteDialogComponent,
  perfilRoute,
  perfilPopupRoute
} from './';

const ENTITY_STATES = [...perfilRoute, ...perfilPopupRoute];

@NgModule({
  imports: [RetailPosSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PerfilComponent, PerfilDetailComponent, PerfilUpdateComponent, PerfilDeleteDialogComponent, PerfilDeletePopupComponent],
  entryComponents: [PerfilComponent, PerfilUpdateComponent, PerfilDeleteDialogComponent, PerfilDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RetailPosSystemPerfilModule {}
