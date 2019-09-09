/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RetailPosSystemTestModule } from '../../../test.module';
import { ImpuestoComponent } from 'app/entities/impuesto/impuesto.component';
import { ImpuestoService } from 'app/entities/impuesto/impuesto.service';
import { Impuesto } from 'app/shared/model/impuesto.model';

describe('Component Tests', () => {
  describe('Impuesto Management Component', () => {
    let comp: ImpuestoComponent;
    let fixture: ComponentFixture<ImpuestoComponent>;
    let service: ImpuestoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RetailPosSystemTestModule],
        declarations: [ImpuestoComponent],
        providers: []
      })
        .overrideTemplate(ImpuestoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImpuestoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImpuestoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Impuesto(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.impuestos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
