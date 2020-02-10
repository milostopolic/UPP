import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewFormatComponent } from './review-format.component';

describe('ReviewFormatComponent', () => {
  let component: ReviewFormatComponent;
  let fixture: ComponentFixture<ReviewFormatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewFormatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewFormatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
