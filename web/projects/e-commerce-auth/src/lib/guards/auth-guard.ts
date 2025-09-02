import { inject } from '@angular/core';
import { CanActivateFn, RedirectCommand, Router } from '@angular/router';
import { AuthService } from '../services/auth-service';
import { AUTH_CONFIG } from '../config';

export const authGuard: CanActivateFn = (route, state) => {
  const config = inject(AUTH_CONFIG);
  const router = inject(Router);
  const authService = inject(AuthService);

  if (!authService.isSignedIn()) {
    const signInPath = router.parseUrl(config.signInPath);

    return new RedirectCommand(signInPath);
  }

  return true;
};
