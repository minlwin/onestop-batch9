import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { computed, inject } from '@angular/core';
import { LoginUserState } from './login-user.state';
import { catchError, switchMap, throwError } from 'rxjs';
import { SecurityService } from '../api/security.service';

export const tokensInterceptor: HttpInterceptorFn = (req, next) => {

  const loginUser = inject(LoginUserState)
  const security = inject(SecurityService)

  const user = computed(() => loginUser.user())

  let newRequest = req

  if(user()) {
    newRequest = req.clone({headers: req.headers.append('Authorization', user()!.accessToken)})
  }

  return next(newRequest).pipe(
    catchError(e => {

      if(e instanceof HttpErrorResponse && e.status == 408 && user()?.refreshToken != undefined) {
        return security.refresh({token : user()!.refreshToken}).pipe(
          switchMap(result => {
            loginUser.setUser(result)
            return next(req.clone({headers: req.headers.append('Authorization', user()!.accessToken)}))
          }),
          catchError(error => {
            loginUser.signOut()
            return throwError(() => error)
          })
        )
      }

      return throwError(() => e)
    })
  );
};
