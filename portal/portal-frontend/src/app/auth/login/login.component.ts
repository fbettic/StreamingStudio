import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../../services/auth/login.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  private loginService: LoginService = inject(LoginService);
  private formBuilder: FormBuilder = inject(FormBuilder);
  private router: Router = inject(Router);

  loginFormGroup: FormGroup;
  loginError: string = '';

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
      console.log('🚀 ~ LoginComponent ~ login ~ credentials:', credentials);

      this.loginService.login(credentials).subscribe({
        next: (userData) => {

        },
        error: (userError) => {
          console.log(
            '🚀 ~ LoginComponent ~ this.loginService.login ~ userError:',
            userError
          );
          this.loginError = userError;
        },
        complete: () => {
          console.log('Success');
          this.router.navigateByUrl('/admin');
          this.loginFormGroup.reset();
        },
      });
    } else {
      alert('Email y/o contraseña incorrectos');
      this.loginFormGroup.markAllAsTouched();
    }
  }
}