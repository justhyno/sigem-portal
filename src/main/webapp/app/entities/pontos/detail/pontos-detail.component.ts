import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPontos } from '../pontos.model';

@Component({
  selector: 'jhi-pontos-detail',
  templateUrl: './pontos-detail.component.html',
})
export class PontosDetailComponent implements OnInit {
  pontos: IPontos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pontos }) => {
      this.pontos = pontos;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
