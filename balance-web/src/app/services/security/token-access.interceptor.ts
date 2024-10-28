import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { LoginUserState } from './login-user.state';

export const tokenAccessInterceptor: HttpInterceptorFn = (req, next) => {

  let newRequest = req
  const loginUser = inject(LoginUserState).user()

  if(loginUser) {
    newRequest = req.clone({headers: req.headers.append('Authorization', loginUser.accessToken)})
  }

  return next(newRequest)
}
