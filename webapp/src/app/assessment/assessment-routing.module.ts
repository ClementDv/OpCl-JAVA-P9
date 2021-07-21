import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AssessmentRiskValueComponent} from "./assessment-risk-value/assessment-risk-value.component";

const routes: Routes = [
 {path: '', component: AssessmentRiskValueComponent},
];

@NgModule({
  imports:[RouterModule.forChild(routes)],
  exports:[RouterModule]
})

export class AssessmentRoutingModule { }
