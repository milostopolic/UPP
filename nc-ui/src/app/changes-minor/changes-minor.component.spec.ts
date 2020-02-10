import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangesMinorComponent } from './changes-minor.component';

describe('ChangesMinorComponent', () => {
  let component: ChangesMinorComponent;
  let fixture: ComponentFixture<ChangesMinorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangesMinorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangesMinorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
