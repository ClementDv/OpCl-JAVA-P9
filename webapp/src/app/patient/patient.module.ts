import {NgModule} from '@angular/core';
import {PatientListComponent} from './patient-list/patient-list.component';
import {PatientRoutingModule} from "./patient-routing.module";
import {SharedModule} from "../shared/shared.module";
import {PatientEditComponent} from './patient-edit/patient-edit.component';
import {PatientAddComponent} from './patient-add/patient-add.component';


@NgModule({
  declarations: [PatientListComponent, PatientEditComponent, PatientAddComponent],
  imports: [
    SharedModule,
    PatientRoutingModule
  ]
})

export class PatientModule { }
