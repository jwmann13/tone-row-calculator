import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ToneRowDisplayComponent } from './tone-row-display/tone-row-display.component';

const routes: Routes = [{
  path: "",
  component: ToneRowDisplayComponent
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
