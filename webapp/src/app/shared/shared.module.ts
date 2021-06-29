import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";


const dependencies = [
  CommonModule,
  MatToolbarModule,
  MatIconModule,
];

@NgModule({
  declarations: [],
  imports: dependencies,
  exports: [...dependencies],
})
export class SharedModule { }
