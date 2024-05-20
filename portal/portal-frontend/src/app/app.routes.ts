import { Routes } from '@angular/router';

import {
  administratorGuard,
  advertiserGuard,
  associationGuard,
  authGuard,
  subscriberGuard,
} from './guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    title: 'StreamingStrudio',
    loadComponent: () => import('./pages/landing/landing.component'),
  },
  {
    path: 'login',
    title: 'Login',
    loadComponent: () => import('./auth/login/login.component'),
  },

  {
    path: 'signup',
    title: 'Signup',
    loadComponent: () => import('./auth/signup/signup.component'),
  },
  {
    path: 'link',
    title: 'Association Processing',
    loadComponent: () =>
      import(
        './pages/subscriber/association-processing/association-processing.component'
      ),
    canActivate: [associationGuard],
  },
  {
    path: 'admin',
    title: 'Dashboard',
    loadComponent: () => import('./pages/admin/dashboard/dashboard.component'),
    canActivate: [authGuard, administratorGuard],
  },
  {
    path: 'advertiser',
    title: 'Dashboard',
    loadComponent: () =>
      import('./pages/advertiser/dashboard/dashboard.component'),
    canActivate: [authGuard],
    canMatch: [advertiserGuard],
  },
  {
    path: 'home',
    title: 'Home',
    loadComponent: () => import('./pages/subscriber/home/home.component'),

    canActivate: [authGuard, subscriberGuard],
  },
  {
    path: 'platforms',
    title: 'Platform Management',
    loadComponent: () =>
      import(
        './pages/subscriber/platform-management/platform-management.component'
      ),
    canActivate: [authGuard, subscriberGuard],
  },
  {
    path: 'play/:id',
    title: 'Play',
    loadComponent: () =>
      import('./pages/subscriber/play-film/play-film.component'),
    canActivate: [authGuard, subscriberGuard],
  },
  {
    path: 'profile',
    title: 'Profile',
    loadComponent: () => import('./pages/subscriber/profile/profile.component'),
    canActivate: [authGuard, subscriberGuard],
  },
  {
    path: '**',
    title: 'Not Found',
    loadComponent: () => import('./pages/not-found/not-found.component'),
  },
];
