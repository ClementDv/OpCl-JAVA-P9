import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {PatientListComponent} from "./patient-list/patient-list.component";
import {PatientAddComponent} from "./patient-add/patient-add.component";
import {PatientEditComponent} from "./patient-edit/patient-edit.component";

const componentRoutes: Routes = [
  {path: '', component: PatientListComponent},
  {path: 'add', component: PatientAddComponent},
  {path: ':id', component: PatientEditComponent},
  {path: ':patientId/note', loadChildren: () => import('../note/note.module').then(m => m.NoteModule)},
  {path: ':patientId/assessment', loadChildren: () => import('../assessment/assessment.module').then(m => m.AssessmentModule)},
];


@NgModule({
  imports: [RouterModule.forChild(componentRoutes)],
  exports: [RouterModule],
})

export class PatientRoutingModule {

}
