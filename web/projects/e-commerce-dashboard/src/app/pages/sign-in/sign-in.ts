import { HttpErrorResponse } from '@angular/common/http';
import { Component, effect, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService, Token } from 'e-commerce-auth';
import { merge } from 'rxjs';

@Component({
  selector: 'app-sign-in',
  imports: [
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    ReactiveFormsModule,
  ],
  templateUrl: './sign-in.html',
  styleUrl: './sign-in.css',
})
export class SignIn {
  signInForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
    ]),
  });
  emailErrorMessage = signal('');
  passwordErrorMessage = signal('');
  passwordHidden = signal(true);
  errorMessage = signal<string | null>(null);

  constructor(
    private router: Router,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {
    merge(
      this.signInForm.controls.email.statusChanges,
      this.signInForm.controls.email.valueChanges
    )
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.updateEmailErrorMessage());

    merge(
      this.signInForm.controls.password.statusChanges,
      this.signInForm.controls.password.valueChanges
    )
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.updatePasswordErrorMessage());

    effect(() => {
      const message = this.errorMessage();

      if (message) {
        snackBar.open(message);
        this.errorMessage.set(null);
      }
    });
  }

  private updateEmailErrorMessage() {
    const email = this.signInForm.controls.email;

    if (email.hasError('required')) {
      this.emailErrorMessage.set('You must enter an email');
    } else if (email.hasError('email')) {
      this.emailErrorMessage.set('Not a valid email');
    } else {
      this.emailErrorMessage.set('');
    }
  }

  private updatePasswordErrorMessage() {
    const password = this.signInForm.controls.password;

    if (password.hasError('required')) {
      this.passwordErrorMessage.set('You must enter a password');
    } else if (password.hasError('minlength')) {
      const requiredLength = password.getError('minlength').requiredLength;

      this.passwordErrorMessage.set(
        `Password must contains at least ${requiredLength} characters`
      );
    } else {
      this.passwordErrorMessage.set('');
    }
  }

  changePasswordVisibility(event: MouseEvent) {
    this.passwordHidden.set(!this.passwordHidden());

    event.stopPropagation();
  }

  signIn() {
    const { email, password } = this.signInForm.value;

    this.authService.signIn(email!, password!).subscribe({
      next: (token: Token) => {
        this.authService.setToken(token.token);
        this.snackBar.open('Successfully signed in');
        this.router.navigate(['/dashboard']);
      },
      error: (error: HttpErrorResponse) => {
        switch (error.status) {
          case 401:
            this.errorMessage.set('Incorrect email or password');
            break;
          case 500:
            this.errorMessage.set('Unable to connect with a server');
            break;
          default:
            this.errorMessage.set('Unknown error occurs');
            break;
        }
      },
    });
  }
}
