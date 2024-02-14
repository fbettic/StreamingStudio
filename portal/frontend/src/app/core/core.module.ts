import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule} from '@angular/material/dialog';

import { LoaderComponent } from './layouts';
import { MessageDialogComponent } from './layouts';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  declarations: [
    LoaderComponent,
    MessageDialogComponent
  ],
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule
  ],
  exports: [
    LoaderComponent
  ]
})
export class CoreModule { }
