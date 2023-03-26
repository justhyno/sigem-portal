import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PontosDetailComponent } from './pontos-detail.component';

describe('Pontos Management Detail Component', () => {
  let comp: PontosDetailComponent;
  let fixture: ComponentFixture<PontosDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PontosDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pontos: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PontosDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PontosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pontos on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pontos).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
