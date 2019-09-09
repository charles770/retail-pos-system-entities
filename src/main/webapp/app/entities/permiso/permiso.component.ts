import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPermiso } from 'app/shared/model/permiso.model';
import { AccountService } from 'app/core';
import { PermisoService } from './permiso.service';

@Component({
  selector: 'jhi-permiso',
  templateUrl: './permiso.component.html'
})
export class PermisoComponent implements OnInit, OnDestroy {
  permisos: IPermiso[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected permisoService: PermisoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.permisoService
      .query()
      .pipe(
        filter((res: HttpResponse<IPermiso[]>) => res.ok),
        map((res: HttpResponse<IPermiso[]>) => res.body)
      )
      .subscribe(
        (res: IPermiso[]) => {
          this.permisos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPermisos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPermiso) {
    return item.id;
  }

  registerChangeInPermisos() {
    this.eventSubscriber = this.eventManager.subscribe('permisoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
