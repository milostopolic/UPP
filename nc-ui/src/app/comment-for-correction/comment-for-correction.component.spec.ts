import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentForCorrectionComponent } from './comment-for-correction.component';

describe('CommentForCorrectionComponent', () => {
  let component: CommentForCorrectionComponent;
  let fixture: ComponentFixture<CommentForCorrectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommentForCorrectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentForCorrectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
