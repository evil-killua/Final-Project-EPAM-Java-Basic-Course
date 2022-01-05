import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewUserPresenceComponent } from './view-user-presence.component';

describe('ViewUserPresenceComponent', () => {
  let component: ViewUserPresenceComponent;
  let fixture: ComponentFixture<ViewUserPresenceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewUserPresenceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewUserPresenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
