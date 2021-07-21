import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentRiskValueComponent } from './assessment-risk-value.component';

describe('AssessmentRiskValueComponent', () => {
  let component: AssessmentRiskValueComponent;
  let fixture: ComponentFixture<AssessmentRiskValueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssessmentRiskValueComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentRiskValueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
