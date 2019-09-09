/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RetailPosSystemTestModule } from '../../../test.module';
import { ProveedorComponent } from 'app/entities/proveedor/proveedor.component';
import { ProveedorService } from 'app/entities/proveedor/proveedor.service';
import { Proveedor } from 'app/shared/model/proveedor.model';

describe('Component Tests', () => {
  describe('Proveedor Management Component', () => {
    let comp: ProveedorComponent;
    let fixture: ComponentFixture<ProveedorComponent>;
    let service: ProveedorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RetailPosSystemTestModule],
        declarations: [ProveedorComponent],
        providers: []
      })
        .overrideTemplate(ProveedorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProveedorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProveedorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Proveedor(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.proveedors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
