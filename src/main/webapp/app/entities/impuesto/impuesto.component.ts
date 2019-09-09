import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IImpuesto } from 'app/shared/model/impuesto.model';
import { AccountService } from 'app/core';
import { ImpuestoService } from './impuesto.service';

@Component({
  selector: 'jhi-impuesto',
  templateUrl: './impuesto.component.html'
})
export class ImpuestoComponent implements OnInit, OnDestroy {
  impuestos: IImpuesto[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected impuestoService: ImpuestoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.impuestoService
      .query()
      .pipe(
        filter((res: HttpResponse<IImpuesto[]>) => res.ok),
        map((res: HttpResponse<IImpuesto[]>) => res.body)
      )
      .subscribe(
        (res: IImpuesto[]) => {
          this.impuestos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInImpuestos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IImpuesto) {
    return item.id;
  }

  registerChangeInImpuestos() {
    this.eventSubscriber = this.eventManager.subscribe('impuestoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
