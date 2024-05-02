import { Routes } from '@angular/router';
import { LandingComponent } from './pages/landing/landing.component';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { DashboardComponent as AdminDashboardComponent } from './pages/admin/dashboard/dashboard.component';
import { HomeComponent } from './pages/subscriber/home/home.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { DashboardComponent as AdvertiserDashboardComponent } from './pages/advertiser/dashboard/dashboard.component';
import {
  administratorGuard,
  advertiserGuard,
  associationGuard,
  authGuard,
  subscriberGuard,
} from './guards/auth.guard';
import { PlatformManagementComponent } from './pages/subscriber/platform-management/platform-management.component';
import { AssociationProcessingComponent } from './pages/subscriber/association-processing/association-processing.component';
import { PlayFilmComponent } from './pages/subscriber/play-film/play-film.component';
import { ProfileComponent } from './pages/subscriber/profile/profile.component';

export const routes: Routes = [
  { path: '', title: 'StreamingStrudio', component: LandingComponent },
  { path: 'login', title: 'Login', component: LoginComponent },
  { path: 'signup', title: 'Signup', component: SignupComponent },
  {
    path: 'link',
    title: 'Association Processing',
    component: AssociationProcessingComponent,
    canActivate: [associationGuard],
  }, 
  {
    path: 'admin',
    title: 'Dashboard',
    component: AdminDashboardComponent,
    canActivate: [authGuard, administratorGuard],
  }, 
  {
    path: 'advertiser',
    title: 'Dashboard',
    component: AdvertiserDashboardComponent,
    canActivate: [authGuard],
    canMatch: [advertiserGuard],
  },
  {
    path: 'home',
    title: 'Home',
    component: HomeComponent,
    canActivate: [authGuard, subscriberGuard],
  },
  {
    path: 'platforms',
    title: 'Platform Management',
    component: PlatformManagementComponent,
    canActivate: [authGuard, subscriberGuard],
  },
  {
    path: 'play/:id',
    title: 'Play',
    component: PlayFilmComponent,
    canActivate: [authGuard, subscriberGuard],
  },
  {
    path: 'profile',
    title: 'Profile',
    component: ProfileComponent,
    canActivate: [authGuard, subscriberGuard],
  },
  { path: '**', title: 'Not Found', component: NotFoundComponent },
];
