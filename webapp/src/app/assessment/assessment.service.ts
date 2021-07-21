import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AssessmentRiskResult} from "./assessment-risk-result";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AssessmentService {

  private readonly url = 'http://localhost:8080/assess/';

  constructor(private http: HttpClient) {}

  assessById(id: number): Observable<AssessmentRiskResult> {
    return this.http.get(`${this.url}${id}`) as Observable<AssessmentRiskResult>;
  }
}
