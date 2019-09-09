import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'producto',
        loadChildren: () => import('./producto/producto.module').then(m => m.RetailPosSystemProductoModule)
      },
      {
        path: 'unidad-medida',
        loadChildren: () => import('./unidad-medida/unidad-medida.module').then(m => m.RetailPosSystemUnidadMedidaModule)
      },
      {
        path: 'proveedor',
        loadChildren: () => import('./proveedor/proveedor.module').then(m => m.RetailPosSystemProveedorModule)
      },
      {
        path: 'categoria',
        loadChildren: () => import('./categoria/categoria.module').then(m => m.RetailPosSystemCategoriaModule)
      },
      {
        path: 'impuesto',
        loadChildren: () => import('./impuesto/impuesto.module').then(m => m.RetailPosSystemImpuestoModule)
      },
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.RetailPosSystemClienteModule)
      },
      {
        path: 'empleado',
        loadChildren: () => import('./empleado/empleado.module').then(m => m.RetailPosSystemEmpleadoModule)
      },
      {
        path: 'perfil',
        loadChildren: () => import('./perfil/perfil.module').then(m => m.RetailPosSystemPerfilModule)
      },
      {
        path: 'permiso',
        loadChildren: () => import('./permiso/permiso.module').then(m => m.RetailPosSystemPermisoModule)
      },
      {
        path: 'pedido',
        loadChildren: () => import('./pedido/pedido.module').then(m => m.RetailPosSystemPedidoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RetailPosSystemEntityModule {}
