import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';
import { AccountService } from 'app/core';
import { UnidadMedidaService } from './unidad-medida.service';

@Component({
  selector: 'jhi-unidad-medida',
  templateUrl: './unidad-medida.component.html'
})
export class UnidadMedidaComponent implements OnInit, OnDestroy {
  unidadMedidas: IUnidadMedida[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected unidadMedidaService: UnidadMedidaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.unidadMedidaService
      .query()
      .pipe(
        filter((res: HttpResponse<IUnidadMedida[]>) => res.ok),
        map((res: HttpResponse<IUnidadMedida[]>) => res.body)
      )
      .subscribe(
        (res: IUnidadMedida[]) => {
          this.unidadMedidas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUnidadMedidas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUnidadMedida) {
    return item.id;
  }

  registerChangeInUnidadMedidas() {
    this.eventSubscriber = this.eventManager.subscribe('unidadMedidaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
