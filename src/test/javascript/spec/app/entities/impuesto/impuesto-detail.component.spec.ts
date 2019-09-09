/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RetailPosSystemTestModule } from '../../../test.module';
import { ImpuestoDetailComponent } from 'app/entities/impuesto/impuesto-detail.component';
import { Impuesto } from 'app/shared/model/impuesto.model';

describe('Component Tests', () => {
  describe('Impuesto Management Detail Component', () => {
    let comp: ImpuestoDetailComponent;
    let fixture: ComponentFixture<ImpuestoDetailComponent>;
    const route = ({ data: of({ impuesto: new Impuesto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RetailPosSystemTestModule],
        declarations: [ImpuestoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ImpuestoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImpuestoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.impuesto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
