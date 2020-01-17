import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseRevAndEdComponent } from './choose-rev-and-ed.component';

describe('ChooseRevAndEdComponent', () => {
  let component: ChooseRevAndEdComponent;
  let fixture: ComponentFixture<ChooseRevAndEdComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChooseRevAndEdComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseRevAndEdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
