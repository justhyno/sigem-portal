import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SpatialUnitDetailComponent } from './spatial-unit-detail.component';

describe('SpatialUnit Management Detail Component', () => {
  let comp: SpatialUnitDetailComponent;
  let fixture: ComponentFixture<SpatialUnitDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SpatialUnitDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ spatialUnit: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SpatialUnitDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SpatialUnitDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load spatialUnit on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.spatialUnit).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
