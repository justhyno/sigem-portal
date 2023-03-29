import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjecto } from '../projecto.model';
import { ProjectoService } from '../service/projecto.service';

@Component({
  selector: 'jhi-projecto-detail',
  templateUrl: './projecto-detail.component.html',
})
export class ProjectoDetailComponent implements OnInit {
  projecto: IProjecto | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected projectoService: ProjectoService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projecto }) => {
      this.projecto = projecto;
    });
  }

  previousState(): void {
    window.history.back();
  }

  printTitulos(): void {
    console.log('id do projecto: ' + this.projecto?.id);

    this.projectoService.printTitulo(this.projecto?.id!).subscribe(() => {});
  }

  confirmDelete(id: number): void {
    this.projectoService.printTitulo(id).subscribe(() => {});
  }
}
