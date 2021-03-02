import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ToneRowDisplayComponent } from './tone-row-display/tone-row-display.component';
import { MatrixDisplayComponent } from './matrix-display/matrix-display.component';
import { ToneRowListComponent } from './tone-row-list/tone-row-list.component';

@NgModule({
  declarations: [
    AppComponent,
    ToneRowDisplayComponent,
    MatrixDisplayComponent,
    ToneRowListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
