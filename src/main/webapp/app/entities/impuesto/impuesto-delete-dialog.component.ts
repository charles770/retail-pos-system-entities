import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImpuesto } from 'app/shared/model/impuesto.model';
import { ImpuestoService } from './impuesto.service';

@Component({
  selector: 'jhi-impuesto-delete-dialog',
  templateUrl: './impuesto-delete-dialog.component.html'
})
export class ImpuestoDeleteDialogComponent {
  impuesto: IImpuesto;

  constructor(protected impuestoService: ImpuestoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.impuestoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'impuestoListModification',
        content: 'Deleted an impuesto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-impuesto-delete-popup',
  template: ''
})
export class ImpuestoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ impuesto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ImpuestoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.impuesto = impuesto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/impuesto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/impuesto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
