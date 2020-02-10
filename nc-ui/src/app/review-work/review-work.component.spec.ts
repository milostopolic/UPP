import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewWorkComponent } from './review-work.component';

describe('ReviewWorkComponent', () => {
  let component: ReviewWorkComponent;
  let fixture: ComponentFixture<ReviewWorkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewWorkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewWorkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
