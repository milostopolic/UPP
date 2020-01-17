import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmMagazineComponent } from './confirm-magazine.component';

describe('ConfirmMagazineComponent', () => {
  let component: ConfirmMagazineComponent;
  let fixture: ComponentFixture<ConfirmMagazineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmMagazineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmMagazineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
