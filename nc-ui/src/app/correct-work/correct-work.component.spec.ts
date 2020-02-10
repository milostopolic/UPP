import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CorrectWorkComponent } from './correct-work.component';

describe('CorrectWorkComponent', () => {
  let component: CorrectWorkComponent;
  let fixture: ComponentFixture<CorrectWorkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CorrectWorkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CorrectWorkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
