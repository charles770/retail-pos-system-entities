import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProveedor } from 'app/shared/model/proveedor.model';
import { AccountService } from 'app/core';
import { ProveedorService } from './proveedor.service';

@Component({
  selector: 'jhi-proveedor',
  templateUrl: './proveedor.component.html'
})
export class ProveedorComponent implements OnInit, OnDestroy {
  proveedors: IProveedor[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected proveedorService: ProveedorService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.proveedorService
      .query()
      .pipe(
        filter((res: HttpResponse<IProveedor[]>) => res.ok),
        map((res: HttpResponse<IProveedor[]>) => res.body)
      )
      .subscribe(
        (res: IProveedor[]) => {
          this.proveedors = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProveedors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProveedor) {
    return item.id;
  }

  registerChangeInProveedors() {
    this.eventSubscriber = this.eventManager.subscribe('proveedorListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
