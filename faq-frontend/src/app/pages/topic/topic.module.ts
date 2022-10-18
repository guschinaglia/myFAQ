import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopicComponent } from './topic.component';
import { TopicRoutingModule } from './topic.routing.module';

@NgModule({
  declarations: [TopicComponent],
  imports: [CommonModule, TopicRoutingModule],
})
export class TopicModule {}
