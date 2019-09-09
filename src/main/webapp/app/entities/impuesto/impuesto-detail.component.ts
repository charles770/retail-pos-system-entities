import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImpuesto } from 'app/shared/model/impuesto.model';

@Component({
  selector: 'jhi-impuesto-detail',
  templateUrl: './impuesto-detail.component.html'
})
export class ImpuestoDetailComponent implements OnInit {
  impuesto: IImpuesto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ impuesto }) => {
      this.impuesto = impuesto;
    });
  }

  previousState() {
    window.history.back();
  }
}
