import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Note} from "./note";

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  private readonly url = 'http://localhost:8082/note/';

  constructor(private http: HttpClient,) {
  }

  getNoteListById(patientId: number): Observable<Note[]> {
    let params = new HttpParams();
    params = params.append('patientId', patientId.toString());
   return this.http.get(this.url, {params: params}) as Observable<Note[]>;
  }

  getOne(id: number): Observable<Note> {
    return this.http.get(`${this.url}${id}`) as Observable<Note>;
  }

  save(note: Note): Observable<any> {
    return this.http.post(this.url, note) as Observable<any>;
  }

  update(note: Note): Observable<any> {
    return this.http.put(this.url, note) as Observable<any>;
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.url}${id}`) as Observable<any>;
  }
}
