import { Injectable, signal } from "@angular/core";

@Injectable({providedIn: 'root'})
export class LoginUserState {

  user = signal<LoginUser | undefined>(undefined)
}

export interface LoginUser {
  loginId:string,
  name:string,
  role:Role,
  accessToken:string,
  refreshToken:string
}

export enum Role {
  Admin, Member
}
