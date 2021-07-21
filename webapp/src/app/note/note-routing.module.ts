import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {NoteListComponent} from "./note-list/note-list.component";
import {NoteAddComponent} from "./note-add/note-add.component";
import {NoteEditComponent} from "./note-edit/note-edit.component";

const routes: Routes = [
  {path: '', component: NoteListComponent},
  {path: 'add', component: NoteAddComponent},
  {path: ':id', component: NoteEditComponent},
];

@NgModule({
  imports:[RouterModule.forChild(routes)],
  exports:[RouterModule]
})

export class NoteRoutingModule { }
