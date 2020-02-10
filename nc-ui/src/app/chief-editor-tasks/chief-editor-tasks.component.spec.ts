import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChiefEditorTasksComponent } from './chief-editor-tasks.component';

describe('ChiefEditorTasksComponent', () => {
  let component: ChiefEditorTasksComponent;
  let fixture: ComponentFixture<ChiefEditorTasksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChiefEditorTasksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChiefEditorTasksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
