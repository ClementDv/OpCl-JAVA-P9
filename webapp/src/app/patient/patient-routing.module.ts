import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {PatientListComponent} from "./patient-list/patient-list.component";
import {PatientAddComponent} from "./patient-add/patient-add.component";
import {PatientEditComponent} from "./patient-edit/patient-edit.component";

const routes: Routes = [
  {path: '', component: PatientListComponent},
  {path: 'add', component: PatientAddComponent},
  {path: ':id', component: PatientEditComponent},
  ];

@NgModule({
  imports:[RouterModule.forChild(routes)],
  exports:[RouterModule]
})

export class PatientRoutingModule {

}
