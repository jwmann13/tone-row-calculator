import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MatrixDisplayComponent } from './matrix-display/matrix-display.component';
import { ToneRowDisplayComponent } from './tone-row-display/tone-row-display.component';
import { ToneRowListComponent } from './tone-row-list/tone-row-list.component';

const routes: Routes = [{
  path: "",
  component: ToneRowListComponent
}, {
  path: "tonerow",
  component: ToneRowDisplayComponent
}, {
  path: "matrix",
  component: MatrixDisplayComponent
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
