import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjecto } from '../projecto.model';

@Component({
  selector: 'jhi-projecto-detail',
  templateUrl: './projecto-detail.component.html',
})
export class ProjectoDetailComponent implements OnInit {
  projecto: IProjecto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projecto }) => {
      this.projecto = projecto;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
