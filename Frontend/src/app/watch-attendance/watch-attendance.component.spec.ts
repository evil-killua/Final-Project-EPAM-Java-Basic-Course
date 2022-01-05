import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WatchAttendanceComponent } from './watch-attendance.component';

describe('WatchAttendanceComponent', () => {
  let component: WatchAttendanceComponent;
  let fixture: ComponentFixture<WatchAttendanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WatchAttendanceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WatchAttendanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
