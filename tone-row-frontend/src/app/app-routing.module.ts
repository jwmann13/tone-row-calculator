import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MatrixDisplayComponent } from './matrix-display/matrix-display.component';
import { NewMatrixComponent } from './new-matrix/new-matrix.component';
import { ToneRowListComponent } from './tone-row-list/tone-row-list.component';

const routes: Routes = [{
  path: "",
  component: ToneRowListComponent
},{
  path: "matrix/new",
  component: NewMatrixComponent
}, {
  path: "matrix/:toneRowId",
  component: MatrixDisplayComponent
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
