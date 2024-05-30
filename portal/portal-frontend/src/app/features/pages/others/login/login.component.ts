import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../../../../core/services/login.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export default class LoginComponent {
  private loginService: LoginService = inject(LoginService);
  private formBuilder: FormBuilder = inject(FormBuilder);
  private router: Router = inject(Router);

  loginFormGroup: FormGroup;
  loginError: string = '';
  userRole = '';

  constructor() {
    this.loginFormGroup = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  get email() {
    return this.loginFormGroup.controls['email'];
  }

  get password() {
    return this.loginFormGroup.controls['password'];
  }

  login() {
    if (this.loginFormGroup.valid) {
      const credentials = this.loginFormGroup.value;

      this.loginService.login(credentials).subscribe({
        next: (userData) => {
          this.userRole = userData.role;
        },
        error: (userError) => {
          this.loginError = userError;
        },
        complete: () => {
          this.loginFormGroup.reset();
          this.goToHomepage(this.userRole);
        },
      });
    } else {
      this.loginFormGroup.markAllAsTouched();
    }
  }

  goToHomepage(role: string) {
    switch (role) {
      case 'SUBSCRIBER':
        this.router.navigateByUrl('/home');
        return;
      case 'ADMINISTRATOR':
        this.router.navigateByUrl('/admin');
        return;
      case 'ADVERTISER':
        this.router.navigateByUrl('/advertiser');
        return;
      default:
        this.router.navigateByUrl('/');
        return;
    }
  }
}
