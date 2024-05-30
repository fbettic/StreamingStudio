import { HttpInterceptorFn } from '@angular/common/http';
import { NgZone, inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { IMessage } from '../models/message.model';
import { ErrorService } from '../services/error.service';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const _errorService: ErrorService = inject(ErrorService);
  const _ngZone: NgZone = inject(NgZone);

  return next(req).pipe(
    catchError((error) => {
      if (error.rejection) {
        error = error.rejection;
      }
      let message: IMessage;
      if (error.body) {
        if (error.body.message) {
          message = { text: error.body.message, code: error.status };
        } else if (error.body.error) {
          message = { text: error.body.error, code: error.status };
        } else {
          if (error.status == 0) {
            message = {
              text: $localize`Error al conectarse al servicio`,
              code: error.status,
            };
          } else {
            message = { text: error.body, code: error.status };
          }
        }
      } else if (error.error) {
        message = { text: error.error, code: error.status };
      } else if (error.message) {
        message = { text: error.message, code: error.status };
      } else {
        message = { text: error, code: error.status };
      }

      _ngZone.run(() => (_errorService.showErrorModal(message)), 0);

      console.log('🚀 ~ ErrorInterceptorService ~ intercept ~ error:', error);
      return throwError(() => error);
    })
  );
};
