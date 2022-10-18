import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TopicComponent } from './topic.component';

@NgModule({
  imports: [RouterModule.forChild([{ path: '', component: TopicComponent }])],
  exports: [RouterModule],
})
export class TopicRoutingModule {}
