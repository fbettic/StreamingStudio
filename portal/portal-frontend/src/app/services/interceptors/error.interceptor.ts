import { HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError(error => {

      console.log("🚀 ~ ErrorInterceptorService ~ intercept ~ error:", error)
      return throwError(()=>error)
    })
  )
};
