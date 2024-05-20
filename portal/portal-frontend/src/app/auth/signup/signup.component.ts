import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { SignupService } from '../../services/auth/signup.service';
import { LoginService } from '../../services/auth/login.service';
import { ILoginResponse } from '../../models/login-response.model';

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
  signupError: string = '';

  constructor() {
    this.signupFormGroup = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      phone: ['', Validators.required],
      birth: ['', Validators.required],
    });
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
      console.log('🚀 ~ SignupComponent ~ signup ~ signupData:', signupData);
      this.signupService.signup(signupData).subscribe({
        next:(userData)=>{
          this.saveUserData(userData)
        },
        error:(error) => {
          console.log("🚀 ~ SignupComponent ~ this.signupService.signup ~ error:", error)
          this.signupError=error;
        },
        complete: () => {
          console.log('Success');
          this.router.navigateByUrl('/admin');
          this.signupFormGroup.reset();
        },
      });
    } else {
      alert('Datos incorrectos');
      this.signupFormGroup.markAllAsTouched();
    }
  }

  private saveUserData(userData: ILoginResponse) {
    this.loginService.setCurrentUserData = userData;
  }
}
