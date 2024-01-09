import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotActivatedUsersComponent } from './not-activated-users.component';

describe('NotActivatedUsersComponent', () => {
  let component: NotActivatedUsersComponent;
  let fixture: ComponentFixture<NotActivatedUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NotActivatedUsersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NotActivatedUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
