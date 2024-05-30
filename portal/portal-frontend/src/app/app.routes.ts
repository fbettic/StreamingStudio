import { Routes } from '@angular/router';

import {
  administratorGuard,
  advertiserGuard,
  associationGuard,
  authGuard,
  subscriberGuard,
} from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    title: 'StreamingStudio',
    loadComponent: () => import('./features/pages/others/landing/landing.component'),
  },
  {
    path: 'login',
    title: 'Login',
    loadComponent: () => import('./features/pages/others/login/login.component'),
  },

  {
    path: 'signup',
    title: 'Signup',
    loadComponent: () => import('./features/pages/subscriber/signup/signup.component'),
  },
  {
    path: 'link',
    title: 'Association Processing',
    loadComponent: () =>
      import(
        './features/pages/subscriber/association-processing/association-processing.component'
      ),
    canActivate: [associationGuard],
  },
  {
    path: 'admin',
    title: 'Dashboard',
    loadComponent: () => import('./features/pages/admin/dashboard/dashboard.component'),
    canActivate: [authGuard, administratorGuard],
  },
  {
    path: 'advertiser',
    title: 'Dashboard',
    loadComponent: () =>
      import('./features/pages/advertiser/dashboard/dashboard.component'),
    canActivate: [authGuard],
    canMatch: [advertiserGuard],
  },
  {
    path: 'home',
    title: 'Home',
    loadComponent: () => import('./features/pages/subscriber/home/home.component'),

    canActivate: [authGuard, subscriberGuard],
  },
  {
    path: 'platforms',
    title: 'Platform Management',
    loadComponent: () =>
      import(
        './features/pages/subscriber/platform-management/platform-management.component'
      ),
    canActivate: [authGuard, subscriberGuard],
  },
  {
    path: 'play/:id',
    title: 'Play',
    loadComponent: () =>
      import('./features/pages/subscriber/play-film/play-film.component'),
    canActivate: [authGuard, subscriberGuard],
  },
  {
    path: 'profile',
    title: 'Profile',
    loadComponent: () => import('./features/pages/subscriber/profile/profile.component'),
    canActivate: [authGuard, subscriberGuard],
  },
  {
    path: '**',
    title: 'Not Found',
    loadComponent: () => import('./features/pages/others/not-found/not-found.component'),
  },
];
