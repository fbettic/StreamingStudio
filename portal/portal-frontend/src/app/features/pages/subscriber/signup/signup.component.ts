import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { ILoginResponse } from '../../../../core/models/login-response.model';
import { LoginService } from '../../../../core/services/login.service';
import { SignupService } from '../../../../shared/services/signup.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss',
})
export default class SignupComponent {
  private signupService: SignupService = inject(SignupService);
  private loginService: LoginService = inject(LoginService);

  private formBuilder: FormBuilder = inject(FormBuilder);
  private router: Router = inject(Router);

  signupFormGroup: FormGroup;
  error: string = '';

  constructor() {
    this.signupFormGroup = this.formBuilder.group(
      {
        email: ['', [Validators.required, Validators.email]],
        password: ['', Validators.required],
        confirmPassword: ['', Validators.required], // Nuevo campo de confirmación de contraseña
        firstname: ['', Validators.required],
        lastname: ['', Validators.required],
        phone: ['', Validators.required],
        birth: ['', Validators.required],
      },
      {
        validator: this.passwordMatchValidator, // Validador personalizado para verificar si las contraseñas coinciden
      }
    );
  }

  passwordMatchValidator(formGroup: FormGroup) {
    const password = formGroup.get('password')?.value;
    const confirmPassword = formGroup.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }

  get confirmPassword() {
    return this.signupFormGroup.controls['confirmPassword'];
  }

  get passwordMismatch() {
    return (
      this.signupFormGroup.hasError('mismatch') &&
      this.signupFormGroup.get('confirmPassword')?.dirty
    );
  }

  get email() {
    return this.signupFormGroup.controls['email'];
  }

  get password() {
    return this.signupFormGroup.controls['password'];
  }

  get firstname() {
    return this.signupFormGroup.controls['firstname'];
  }

  get lastname() {
    return this.signupFormGroup.controls['lastname'];
  }

  get phone() {
    return this.signupFormGroup.controls['phone'];
  }

  get birth() {
    return this.signupFormGroup.controls['birth'];
  }

  signup() {
    if (this.signupFormGroup.valid) {
      const signupData = this.signupFormGroup.value;

      this.signupService.signup(signupData).subscribe({
        next: (userData) => {
          this.saveUserData(userData);
        },
        error: (error) => {
          this.error = error;
        },
        complete: () => {
          this.router.navigateByUrl('/admin');
          this.signupFormGroup.reset();
        },
      });
    } else {
      this.signupFormGroup.markAllAsTouched();
    }
  }

  private saveUserData(userData: ILoginResponse) {
    this.loginService.setCurrentUserData = userData;
  }
}
