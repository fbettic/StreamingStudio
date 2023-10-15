import { NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { HomeRoutingModule } from './home-routing.module';
import { FilmsGridComponent, FilmsCarouselComponent } from 'src/app/components';


@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    FilmsGridComponent,
    FilmsCarouselComponent
  ],
})
export class HomeModule { }
