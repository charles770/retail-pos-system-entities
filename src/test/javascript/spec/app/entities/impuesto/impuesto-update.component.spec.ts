/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { RetailPosSystemTestModule } from '../../../test.module';
import { ImpuestoUpdateComponent } from 'app/entities/impuesto/impuesto-update.component';
import { ImpuestoService } from 'app/entities/impuesto/impuesto.service';
import { Impuesto } from 'app/shared/model/impuesto.model';

describe('Component Tests', () => {
  describe('Impuesto Management Update Component', () => {
    let comp: ImpuestoUpdateComponent;
    let fixture: ComponentFixture<ImpuestoUpdateComponent>;
    let service: ImpuestoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RetailPosSystemTestModule],
        declarations: [ImpuestoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ImpuestoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImpuestoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImpuestoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Impuesto(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Impuesto();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
