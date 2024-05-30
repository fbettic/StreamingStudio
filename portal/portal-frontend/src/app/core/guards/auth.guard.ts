import { inject } from '@angular/core';
import { CanActivateFn, CanMatchFn, Router } from '@angular/router';
import { map } from 'rxjs';
import { LoginService } from '../services/login.service';

export const authGuard: CanActivateFn = (route, state) => {
  const loginService: LoginService = inject(LoginService);
  const router: Router = inject(Router);
  return loginService.userLoggedIn.pipe(
    map((isLoggedIn) => {
      if (isLoggedIn) {
        return true;
      } else {
        return router.createUrlTree(['/login']);
      }
    })
  );
};

export const associationGuard: CanActivateFn = (route, state) => {
  return route.queryParams.hasOwnProperty('uuid');
};

export const subscriberGuard: CanActivateFn = (route, state) => {
  const loginService: LoginService = inject(LoginService);
  const router: Router = inject(Router);
  return loginService.userData.pipe(
    map((userData) => {
      const role = userData.role;
      if (role === 'SUBSCRIBER') {
        return true;
      } else {
        return router.createUrlTree(['/login']);
      }
    })
  );
};

export const administratorGuard: CanActivateFn = (route, state) => {
  const loginService: LoginService = inject(LoginService);
  const router: Router = inject(Router);
  return loginService.userData.pipe(
    map((userData) => {
      const role = userData.role;
      if (role === 'ADMINISTRATOR') {
        return true;
      } else {
        return router.createUrlTree(['/login']);
      }
    })
  );
};

export const advertiserGuard: CanMatchFn = (route, state) => {
  const loginService: LoginService = inject(LoginService);
  return loginService.userData.pipe(
    map((userData) => {
      const role = userData.role;
      if (role === 'ADVERTISER') {
        return true;
      } else {
        return false;
      }
    })
  );
};
