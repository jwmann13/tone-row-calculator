import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EditMatrixComponent } from './edit-matrix/edit-matrix.component';
import { NewMatrixComponent } from './new-matrix/new-matrix.component';
import { ShowMatrixComponent } from './show-matrix/show-matrix.component';
import { ToneRowListComponent } from './tone-row-list/tone-row-list.component';

const routes: Routes = [{
  path: "",
  component: ToneRowListComponent
},{
  path: "matrix/new",
  component: NewMatrixComponent
}, {
  path: "matrix/:toneRowId",
  component: ShowMatrixComponent
}, {
  path: "matrix/edit/:toneRowId",
  component: EditMatrixComponent
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
