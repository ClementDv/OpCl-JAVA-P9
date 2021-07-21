import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PatientService} from "../patient.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

@Component({
  selector: 'app-patient-add',
  templateUrl: './patient-add.component.html',
  styleUrls: ['./patient-add.component.scss']
})
export class PatientAddComponent implements OnInit {

  patientForm = new FormGroup({
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    gender: new FormControl('', [Validators.required]),
    birthDate: new FormControl(null, [Validators.required]),
    phoneNumber: new FormControl(''),
    address: new FormControl(''),
  });

  constructor(
    private patientService: PatientService,
    private snackBar: MatSnackBar,
    private router: Router,
  ) {
  }

  ngOnInit(): void {

  }

  get firstNameControl(): FormControl {
    return this.patientForm.get('firstName') as FormControl;
  }

  get lastNameControl(): FormControl {
    return this.patientForm.get('lastName') as FormControl;
  }

  get genderControl(): FormControl {
    return this.patientForm.get('gender') as FormControl;
  }

  get birthDateControl(): FormControl {
    return this.patientForm.get('birthDate') as FormControl;
  }

  submit(): void {
    if (this.patientForm.valid) {
      this.patientService.save(this.patientForm.value).subscribe(() => {
        this.snackBar.open('Patient added', 'close');
        this.router.navigate(['/patient']);
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/patient']);
  }
}
