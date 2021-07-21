import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {NoteService} from "../note.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ActivatedRoute, Router} from "@angular/router";
import {Note} from "../note";
import {Location} from "@angular/common";

@Component({
  selector: 'app-note-add',
  templateUrl: './note-add.component.html',
  styleUrls: ['./note-add.component.scss']
})
export class NoteAddComponent implements OnInit {

  noteForm = new FormGroup({
    content: new FormControl('', [Validators.required]),
  });

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
  }

  get contentControl(): FormControl {
    return this.noteForm.get('content') as FormControl;
  }

  submit(): void {
    if (this.noteForm.valid) {
      const note = new Note({
        noteContent: this.contentControl.value,
        patientId: this.activatedRoute.snapshot.params.patientId,
      });
      this.noteService.save(note).subscribe(() => {
        this.snackBar.open('Notes added', 'close');
        this.location.back();
      });
    }
  }

  cancel(): void {
    this.location.back();
  }
}
