import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { AppComponent } from './app.component';
import { ToneRowDisplayComponent } from './tone-row-display/tone-row-display.component';
import { MatrixDisplayComponent } from './matrix-display/matrix-display.component';
import { ToneRowListComponent, NgbSortableHeader } from './tone-row-list/tone-row-list.component';
import { NewMatrixComponent } from './new-matrix/new-matrix.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RowStaffDisplayComponent } from './row-staff-display/row-staff-display.component';
import { ShowMatrixComponent } from './show-matrix/show-matrix.component';

@NgModule({
  declarations: [
    AppComponent,
    ToneRowDisplayComponent,
    MatrixDisplayComponent,
    ToneRowListComponent,
    NgbSortableHeader,
    NewMatrixComponent,
    RowStaffDisplayComponent,
    ShowMatrixComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    FontAwesomeModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
