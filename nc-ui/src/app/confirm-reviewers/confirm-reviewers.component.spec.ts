import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmReviewersComponent } from './confirm-reviewers.component';

describe('ConfirmReviewersComponent', () => {
  let component: ConfirmReviewersComponent;
  let fixture: ComponentFixture<ConfirmReviewersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmReviewersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmReviewersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
