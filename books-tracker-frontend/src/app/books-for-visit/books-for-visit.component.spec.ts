import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BooksForVisitComponent} from './books-for-visit.component';

describe('BooksForVisitComponent', () => {
  let component: BooksForVisitComponent;
  let fixture: ComponentFixture<BooksForVisitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BooksForVisitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BooksForVisitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
