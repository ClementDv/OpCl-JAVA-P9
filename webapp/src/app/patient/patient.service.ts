import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Patient} from "./patient";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private readonly url= 'http://localhost:8081/patient/';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Patient[]> {
    return this.http.get(this.url) as Observable<Patient[]>;
  }
}
