import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { LoginUserState } from './login-user.state';

export const tokensInterceptor: HttpInterceptorFn = (req, next) => {

  const loginUser = inject(LoginUserState).user()
  let newRequest = req

  if(loginUser) {
    newRequest = req.clone({headers: req.headers.append('Authorization', loginUser.accessToken)})
  }

  return next(newRequest);
};
