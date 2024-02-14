import { ErrorHandler, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { FilmsGridComponent } from './components/films-grid/films-grid.component';
import { FilmCardComponent } from './components';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ResourceModule } from '@kkoehn/ngx-resource-handler-ngx-http';
import { AppHttpInterceptor } from './core/interceptors/app-http.interceptor';
import { AppErrorHandler } from './core/handlers/app-error-handler';
import { CoreModule } from './core/core.module';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ToolbarComponent,
    FilmsGridComponent,
    FilmCardComponent,
    HttpClientModule,
    CoreModule,
    ResourceModule.forRoot(),
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AppHttpInterceptor, multi: true },
    { provide: ErrorHandler, useClass: AppErrorHandler },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
