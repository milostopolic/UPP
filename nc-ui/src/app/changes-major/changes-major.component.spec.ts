import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangesMajorComponent } from './changes-major.component';

describe('ChangesMajorComponent', () => {
  let component: ChangesMajorComponent;
  let fixture: ComponentFixture<ChangesMajorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangesMajorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangesMajorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
