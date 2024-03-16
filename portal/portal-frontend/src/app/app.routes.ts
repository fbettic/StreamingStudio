import { Routes } from '@angular/router';
import { LandingComponent } from './pages/landing/landing.component';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { AdminDashboardComponent } from './pages/admin-dashboard/admin-dashboard.component';

export const routes: Routes = [
  { path: '', title: 'StreamingStrudio', component: LandingComponent },
  { path: 'login', title: 'Login', component: LoginComponent },
  { path: 'signup', title: 'Signup', component: SignupComponent },
  { path: 'admin', title: 'Admin Dashboard', component: AdminDashboardComponent },
];
