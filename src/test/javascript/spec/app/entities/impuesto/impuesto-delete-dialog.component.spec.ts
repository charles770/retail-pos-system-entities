/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RetailPosSystemTestModule } from '../../../test.module';
import { ImpuestoDeleteDialogComponent } from 'app/entities/impuesto/impuesto-delete-dialog.component';
import { ImpuestoService } from 'app/entities/impuesto/impuesto.service';

describe('Component Tests', () => {
  describe('Impuesto Management Delete Component', () => {
    let comp: ImpuestoDeleteDialogComponent;
    let fixture: ComponentFixture<ImpuestoDeleteDialogComponent>;
    let service: ImpuestoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RetailPosSystemTestModule],
        declarations: [ImpuestoDeleteDialogComponent]
      })
        .overrideTemplate(ImpuestoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImpuestoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImpuestoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
