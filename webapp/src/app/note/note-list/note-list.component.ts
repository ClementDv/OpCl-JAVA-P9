import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Note} from "../note";
import {NoteService} from "../note.service";
import {map, switchMap, tap} from "rxjs/operators";
import {Patient} from "../../patient/patient";
import {PatientService} from "../../patient/patient.service";
import {forkJoin, Observable} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-note-list',
  templateUrl: './note-list.component.html',
  styleUrls: ['./note-list.component.scss']
})
export class NoteListComponent implements OnInit {

  patient: Patient;

  notes: Note[];

  reactiveData$: Observable<[Note[], Patient]>;

  constructor(
    private activateRouter: ActivatedRoute,
    private router: Router,
    private noteService: NoteService,
    private patientService: PatientService,
    private snackBar: MatSnackBar,
  ) {
  }

  ngOnInit(): void {
    this.reactiveData$ = this.activateRouter.params.pipe(
      map((params: Params) => params.patientId),
      switchMap(patientId => forkJoin([
          this.noteService.getNoteListById(patientId).pipe(tap(notes => this.notes = notes)),
          this.patientService.getOne(patientId).pipe(tap(patient => this.patient = patient)),
        ]
      )));
    this.reactiveData$.subscribe();
  }

  delete(id: number): void {
    this.noteService.delete(id)
      .pipe(
        tap(() => this.snackBar.open('Note deleted', 'close')),
        switchMap(() => this.reactiveData$)
      )
      .subscribe();
  }

  backArrow() {
    this.router.navigate(['patient']);
  }
}
