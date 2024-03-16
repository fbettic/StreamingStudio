import { HttpInterceptorFn } from '@angular/common/http';
import { LoginService } from '../auth/login.service';
import { inject } from '@angular/core';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const loginService: LoginService = inject(LoginService);

  const token: string = loginService.userToken;

  let headers = req.headers;

  // Verificar si el encabezado 'Authorization' no está presente en la solicitud
  if (!req.headers.has('Authorization') && token) {
    // Agregar el encabezado 'Authorization' si el token está disponible
    headers = headers.set('Authorization', `Bearer ${token}`);
  }

  // Verificar si el encabezado 'Content-Type' no está presente en la solicitud
  if (!req.headers.has('Content-Type')) {
    // Agregar el encabezado 'Content-Type' con el valor predeterminado
    headers = headers.set('Content-Type', 'application/json;charset=utf-8');
  }

  // Clonar la solicitud con los encabezados actualizados
  const authReq = req.clone({ headers });

  // Pasar la solicitud clonada al siguiente manipulador
  return next(authReq);
};
