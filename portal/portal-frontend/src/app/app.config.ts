import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { routes } from './app.routes';
import { errorInterceptor } from './services/interceptors/error.interceptor';
import { jwtInterceptor } from './services/interceptors/jwt.interceptor';
import { loadingScreenInterceptor } from './services/interceptors/loading-screen.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([
        loadingScreenInterceptor,
        jwtInterceptor,
        errorInterceptor,
      ])
    ),
  ],
};
