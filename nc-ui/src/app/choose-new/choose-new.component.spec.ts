import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseNewComponent } from './choose-new.component';

describe('ChooseNewComponent', () => {
  let component: ChooseNewComponent;
  let fixture: ComponentFixture<ChooseNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChooseNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
