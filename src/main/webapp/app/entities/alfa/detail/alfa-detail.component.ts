import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlfa } from '../alfa.model';

@Component({
  selector: 'jhi-alfa-detail',
  templateUrl: './alfa-detail.component.html',
})
export class AlfaDetailComponent implements OnInit {
  alfa: IAlfa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alfa }) => {
      this.alfa = alfa;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
