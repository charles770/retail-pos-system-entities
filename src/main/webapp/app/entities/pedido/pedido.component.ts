import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPedido } from 'app/shared/model/pedido.model';
import { AccountService } from 'app/core';
import { PedidoService } from './pedido.service';

@Component({
  selector: 'jhi-pedido',
  templateUrl: './pedido.component.html'
})
export class PedidoComponent implements OnInit, OnDestroy {
  pedidos: IPedido[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pedidoService: PedidoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pedidoService
      .query()
      .pipe(
        filter((res: HttpResponse<IPedido[]>) => res.ok),
        map((res: HttpResponse<IPedido[]>) => res.body)
      )
      .subscribe(
        (res: IPedido[]) => {
          this.pedidos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPedidos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPedido) {
    return item.id;
  }

  registerChangeInPedidos() {
    this.eventSubscriber = this.eventManager.subscribe('pedidoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
