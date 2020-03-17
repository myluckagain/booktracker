import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MyBookNamesComponent} from './my-book-names.component';

describe('MyBookNamesComponent', () => {
  let component: MyBookNamesComponent;
  let fixture: ComponentFixture<MyBookNamesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyBookNamesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyBookNamesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
