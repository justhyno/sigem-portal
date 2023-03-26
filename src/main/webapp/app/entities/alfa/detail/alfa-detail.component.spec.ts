import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AlfaDetailComponent } from './alfa-detail.component';

describe('Alfa Management Detail Component', () => {
  let comp: AlfaDetailComponent;
  let fixture: ComponentFixture<AlfaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AlfaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ alfa: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AlfaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AlfaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load alfa on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.alfa).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
