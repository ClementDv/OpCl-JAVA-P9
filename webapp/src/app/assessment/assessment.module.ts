import {NgModule} from '@angular/core';
import {SharedModule} from "../shared/shared.module";
import { AssessmentRiskValueComponent } from './assessment-risk-value/assessment-risk-value.component';
import {AssessmentRoutingModule} from "./assessment-routing.module";
import {RisqueLabelPipe} from "./risque-label.pipe";


@NgModule({
  declarations: [AssessmentRiskValueComponent, RisqueLabelPipe],
  imports: [
    SharedModule,
    AssessmentRoutingModule
  ]
})

export class AssessmentModule {
}
