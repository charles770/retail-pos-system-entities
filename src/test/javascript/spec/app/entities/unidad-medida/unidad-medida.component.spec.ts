/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RetailPosSystemTestModule } from '../../../test.module';
import { UnidadMedidaComponent } from 'app/entities/unidad-medida/unidad-medida.component';
import { UnidadMedidaService } from 'app/entities/unidad-medida/unidad-medida.service';
import { UnidadMedida } from 'app/shared/model/unidad-medida.model';

describe('Component Tests', () => {
  describe('UnidadMedida Management Component', () => {
    let comp: UnidadMedidaComponent;
    let fixture: ComponentFixture<UnidadMedidaComponent>;
    let service: UnidadMedidaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RetailPosSystemTestModule],
        declarations: [UnidadMedidaComponent],
        providers: []
      })
        .overrideTemplate(UnidadMedidaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UnidadMedidaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UnidadMedidaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UnidadMedida(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.unidadMedidas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
