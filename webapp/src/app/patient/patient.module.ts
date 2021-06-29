import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientListComponent } from './patient-list/patient-list.component';



@NgModule({
  declarations: [PatientListComponent],
  imports: [
    CommonModule
  ]
})
export class PatientModule { }
