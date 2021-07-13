import { Component, OnInit } from '@angular/core';
import {PatientService} from "../patient.service";
import {Patient} from "../patient";

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.scss']
})
export class PatientListComponent implements OnInit {

  patients: Patient[] = [];

  constructor(private patientService: PatientService) { }

  ngOnInit(): void {
    this.patientService.getAll().subscribe((responseHttp: Patient[]) => this.patients = responseHttp, console.warn);
  }

}
