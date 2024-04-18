import { Routes } from '@angular/router';
import { LandingComponent } from './pages/landing/landing.component';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { AdminDashboardComponent } from './pages/admin-dashboard/admin-dashboard.component';
import { SubscriberHomeComponent } from './pages/subscriber-home/subscriber-home.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { AdvertiserDashboardComponent } from './pages/advertiser-dashboard/advertiser-dashboard.component';
import {
  administratorGuard,
  advertiserGuard,
  authGuard,
  subscriberGuard,
} from './guards/auth.guard';

export const routes: Routes = [
  { path: '', title: 'StreamingStrudio', component: LandingComponent },
  { path: 'login', title: 'Login', component: LoginComponent },
  { path: 'signup', title: 'Signup', component: SignupComponent },
  {
    path: 'admin',
    title: 'Dashboard',
    component: AdminDashboardComponent,
    canActivate: [authGuard, administratorGuard],
  }, // Tambien se podria usar canMatch para que los dashboards de admin y advertiser esten en el mismo path
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
    component: SubscriberHomeComponent,
    canActivate: [authGuard, subscriberGuard],
  }, //Subscriber
  { path: '**', title: 'Not Found', component: NotFoundComponent },
];
