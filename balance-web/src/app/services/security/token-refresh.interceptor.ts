import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { LoginUserState } from './login-user.state';
import { catchError, switchMap, throwError } from 'rxjs';
import { SecurityService } from '../api/security.service';

export const tokenRefreshInterceptor: HttpInterceptorFn = (req, next) => {

  const security = inject(SecurityService)
  const loginUser = inject(LoginUserState)

  return next(req).pipe(
    catchError(e => {
      if(e instanceof HttpErrorResponse) {

        if(e.status == 408 && loginUser.user()?.refreshToken != undefined) {
          return security.refresh({token : loginUser.user()?.refreshToken}).pipe(
            switchMap(result => {
              loginUser.setUser(result)
              return next(req.clone({headers: req.headers.set('Authorization', result.accessToken)}))
            }),
            catchError(error => {
              loginUser.signOut()
              return throwError(() => error)
            })
          )
        }

        if(e.status == 401) {
          loginUser.signOut()
          throw new NeedToLoginError(e)
        }
      }

      return throwError(() => e)
    })
  );

};

export class NeedToLoginError {
  readonly error = ['You need to login again.']
  readonly cause:any

  constructor(cause:any) {
    this.cause = cause
  }
}
