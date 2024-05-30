import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { finalize } from 'rxjs';
import { LoadingScreenService } from '../services/loading-screen.service';

export const loadingScreenInterceptor: HttpInterceptorFn = (req, next) => {
  const _loadingScreenService: LoadingScreenService =
    inject(LoadingScreenService);

  _loadingScreenService.show();

  return next(req).pipe(finalize(() => _loadingScreenService.hide()));
};
