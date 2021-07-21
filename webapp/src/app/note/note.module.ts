import {NgModule} from '@angular/core';
import {NoteListComponent} from './note-list/note-list.component';
import {SharedModule} from "../shared/shared.module";
import {NoteRoutingModule} from "./note-routing.module";
import { NoteAddComponent } from './note-add/note-add.component';
import { NoteEditComponent } from './note-edit/note-edit.component';


@NgModule({
  declarations: [NoteListComponent, NoteAddComponent, NoteEditComponent],
  imports: [
    SharedModule,
    NoteRoutingModule
  ]
})
export class NoteModule { }
