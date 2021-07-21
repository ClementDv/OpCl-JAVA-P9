import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Patient} from "./patient";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private readonly url = 'http://localhost:8081/patient/';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Patient[]> {
    return this.http.get(this.url) as Observable<Patient[]>;
  }

  getOne(id: number): Observable<Patient> {
    return this.http.get(`${this.url}${id}`) as Observable<Patient>;
  }

  save(patient: Patient): Observable<any> {
    return this.http.post(this.url, patient) as Observable<any>;
  }

  update(patient: Patient): Observable<any> {
    return this.http.put(this.url, patient) as Observable<any>;
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.url}${id}`) as Observable<any>;
  }
}
