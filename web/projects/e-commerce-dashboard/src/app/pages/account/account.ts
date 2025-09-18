import { Component, effect, signal } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { EmployeeService } from '../../services/employee/employee-service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { merge } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { HttpErrorResponse } from '@angular/common/http';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-account',
  imports: [
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    ReactiveFormsModule,
  ],
  templateUrl: './account.html',
  styleUrl: './account.css',
})
export class Account {
  changePasswordForm = new FormGroup({
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
    ]),
  });
  passwordErrorMessage = signal('');
  passwordHidden = signal(true);
  errorMessage = signal<string | null>(null);

  constructor(
    private employeeService: EmployeeService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    merge(
      this.changePasswordForm.controls.password.statusChanges,
      this.changePasswordForm.controls.password.valueChanges
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

  private updatePasswordErrorMessage() {
    const password = this.changePasswordForm.controls.password;

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

  changePassword() {
    const password = this.changePasswordForm.controls.password.value;

    this.employeeService.changePassword(password!).subscribe({
      next: (_) => {
        this.snackBar.open('Successfuly changed password');
      },
      error: (error: HttpErrorResponse) => {
        switch (error.status) {
          case 401:
            this.router.navigate(['/sign-in'], { replaceUrl: true });
            break;
          case 500:
            this.errorMessage.set('Unable to connect with a server');
            break;
          default:
            console.log(error);
            this.errorMessage.set('Unknown error occurs');
            break;
        }
      },
    });
  }
}
