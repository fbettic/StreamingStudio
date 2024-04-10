import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss',
})
export class SignupComponent {
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
      const userData = this.signupFormGroup.value;
      console.log('🚀 ~ SignupComponent ~ signup ~ userData:', userData);
    } else {
      alert('Datos incorrectos');
      this.signupFormGroup.markAllAsTouched();
    }
  }
}
