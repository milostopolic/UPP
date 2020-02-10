import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewBasicInfoComponent } from './review-basic-info.component';

describe('ReviewBasicInfoComponent', () => {
  let component: ReviewBasicInfoComponent;
  let fixture: ComponentFixture<ReviewBasicInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewBasicInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewBasicInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
