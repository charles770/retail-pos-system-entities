import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPerfil } from 'app/shared/model/perfil.model';
import { AccountService } from 'app/core';
import { PerfilService } from './perfil.service';

@Component({
  selector: 'jhi-perfil',
  templateUrl: './perfil.component.html'
})
export class PerfilComponent implements OnInit, OnDestroy {
  perfils: IPerfil[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected perfilService: PerfilService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.perfilService
      .query()
      .pipe(
        filter((res: HttpResponse<IPerfil[]>) => res.ok),
        map((res: HttpResponse<IPerfil[]>) => res.body)
      )
      .subscribe(
        (res: IPerfil[]) => {
          this.perfils = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPerfils();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPerfil) {
    return item.id;
  }

  registerChangeInPerfils() {
    this.eventSubscriber = this.eventManager.subscribe('perfilListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
