import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {NoteService} from "../note.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Location} from "@angular/common";
import {Note} from "../note";
import {map, switchMap, tap} from "rxjs/operators";

@Component({
  selector: 'app-note-edit',
  templateUrl: './note-edit.component.html',
  styleUrls: ['./note-edit.component.scss']
})
export class NoteEditComponent implements OnInit {

  noteForm = new FormGroup({
    content: new FormControl('', [Validators.required]),
  });

  note: Note;

  patientId: number;

  constructor(
    private noteService: NoteService,
    private snackBar: MatSnackBar,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private location: Location,
  ) {
  }


  ngOnInit(): void {
    this.activatedRoute.params.pipe(
      map((params: Params) => params.id),
      switchMap(id => this.noteService.getOne(id)),
      tap(note => this.note = note)
    ).subscribe((note: Note) => this.noteForm.patchValue({content: note.noteContent}));
  }

  get contentControl(): FormControl {
    return this.noteForm.get('content') as FormControl;
  }

  submit(): void {
    if (this.noteForm.value) {
      const note = new Note({
        noteContent: this.contentControl.value,
        id: this.activatedRoute.snapshot.params.id,
        patientId: this.activatedRoute.snapshot.params.patientId,
      });
      this.noteService.update(note).subscribe(() => {
        this.snackBar.open('Note updated', 'close');
        this.location.back();
      });
    }
  }

  cancel(): void {
    this.location.back();
  }
}
